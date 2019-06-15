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
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.crazyx.flinkgo.config.AuthUser;
import vn.crazyx.flinkgo.config.ResponseFail;
import vn.crazyx.flinkgo.config.ResponseSuccess;
import vn.crazyx.flinkgo.dao.User;
import vn.crazyx.flinkgo.service.token.JWTTokenService;
import vn.crazyx.flinkgo.service.token.TokenService;
import vn.crazyx.flinkgo.service.user.UserService;

public class FlinkgoRefreshAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger log = LoggerFactory.getLogger(FlinkgoRefreshAuthenticationFilter.class);
    private UserService userService;
    
    private TokenService tokenService;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    private static long expireTime;
    
    public FlinkgoRefreshAuthenticationFilter(String defaultFilterProcessesUrl, UserService userService, TokenService tokenService) {
        super(defaultFilterProcessesUrl);
        this.userService = userService;
        this.tokenService = tokenService;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        try {
            String refreshToken = request.getHeader("Authorization");
            String tokenType = refreshToken.substring(0, refreshToken.indexOf(' '));
            String token = refreshToken.substring(refreshToken.indexOf(' ') + 1);
            
            log.info("tokenType: " + tokenType + ".");
            
            log.info(token);
            
            if (tokenType.equals("Refresh")) {
                User user = userService.getUserByRefreshToken(token);
                
                if (user == null) {
                    log.warn("user not existed");
                    throw new UsernameNotFoundException("login failed");               
                } else if (!user.getRefreshToken().equals(token)) {
                    log.warn("invalid token");
                    throw new BadCredentialsException("login failed");
                }
                
                long currentTime = System.currentTimeMillis();
                String accessToken = tokenService.genJWT(user, currentTime);
                
                expireTime = currentTime + JWTTokenService.EXPIRE_TIME * 1000;
                
                List<SimpleGrantedAuthority> grantedAuth = new ArrayList<>();
                grantedAuth.add(new SimpleGrantedAuthority("user"));
                
                log.warn("user: " + user.getUserName());
                
                FlinkgoAuthenticationToken auth = 
                        new FlinkgoAuthenticationToken(new AuthUser(user), accessToken);
                auth.setRefreshToken(user.getRefreshToken());
                
                return auth;
            } else {
                log.warn("invalid token");
                throw new BadCredentialsException("login failed");
            }
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
        
        SecurityContextHolder.getContext().setAuthentication(authResult);
        
        ResponseSuccess res = new ResponseSuccess(response.getStatus(), "success");
        res.addResults("accessToken", authResult.getCredentials())
                .addResults("refreshToken", auth.getRefreshToken())
                .addResults("user", authResult.getPrincipal())
                .addResults("expireTime", expireTime);
        
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().print(mapper.writeValueAsString(res));
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
