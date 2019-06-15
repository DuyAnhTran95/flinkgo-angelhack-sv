package vn.crazyx.flinkgo.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.crazyx.flinkgo.config.AuthUser;
import vn.crazyx.flinkgo.config.LoginRequest;
import vn.crazyx.flinkgo.config.ResponseFail;
import vn.crazyx.flinkgo.config.ResponseSuccess;
import vn.crazyx.flinkgo.dao.User;
import vn.crazyx.flinkgo.service.token.JWTTokenService;
import vn.crazyx.flinkgo.service.token.TokenService;
import vn.crazyx.flinkgo.service.user.UserService;
import vn.crazyx.flinkgo.utilities.RandomUtils;

public class FlinkgoLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger log = LoggerFactory.getLogger("LoginFilter");
    
    private UserService userService;
    private TokenService tokenService;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    private static long expireTime;
    
    public FlinkgoLoginAuthenticationFilter(String defaultFilterProcessesUrl, UserService userService, TokenService tokenService) {
        super(defaultFilterProcessesUrl);   
        this.userService = userService;
        this.tokenService = tokenService;
    }
    
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
                
        try {
            LoginRequest req = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            if (req == null)
                log.warn("can't read request");
            
            log.info(req.getUserName());
            
            User user = userService.findByUserName(req.getUserName());
            
            if (user == null) {
                log.warn("user not existed");
                throw new UsernameNotFoundException("login failed");               
            } else if (!encoder().matches(req.getPassword(), user.getPassword())) {
                log.warn("wrong password");
                throw new BadCredentialsException("login failed");
            }
            
            long currentTime = System.currentTimeMillis();
            String token = tokenService.genJWT(user, currentTime);
            
            expireTime = currentTime + JWTTokenService.EXPIRE_TIME * 1000;
            
            List<SimpleGrantedAuthority> grantedAuth = new ArrayList<>();
            grantedAuth.add(new SimpleGrantedAuthority("user"));
            
            if (user.getRefreshToken() == null) {
                user.setRefreshToken(user.getId() + RandomUtils.genRandomString(60));
                userService.save(user);
            }
            
            AuthUser aUser = new AuthUser(user);
            log.debug(aUser.toString());
            
            FlinkgoAuthenticationToken auth = 
                    new FlinkgoAuthenticationToken(aUser, token);
            auth.setRefreshToken(user.getRefreshToken());
            
            return auth;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new AuthenticationCredentialsNotFoundException("login failed");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("login failed");
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        FlinkgoAuthenticationToken auth = (FlinkgoAuthenticationToken) authResult;
        
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        ResponseSuccess res = new ResponseSuccess(response.getStatus(), "success");
        res.addResults("accessToken", auth.getCredentials())
                .addResults("refreshToken", auth.getRefreshToken())
                .addResults("user", auth.getPrincipal())
                .addResults("expireTime", expireTime);
        
        String resp = mapper.writeValueAsString(res);
        
        log.debug(resp);
        
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().print(resp);
    }
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        
        ResponseFail res = new ResponseFail(response.getStatus(), failed);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        
        response.getWriter().print(new ObjectMapper().writeValueAsString(res));
    }
}
