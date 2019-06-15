package vn.crazyx.flinkgo.config.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import vn.crazyx.flinkgo.service.token.TokenService;

public class FlinkgoUserDetailAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    static final Logger log = LoggerFactory.getLogger(FlinkgoUserDetailAuthenticationProvider.class);

    private TokenService tokenService;

    public FlinkgoUserDetailAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
               
        FlinkgoAuthenticationToken auth = (FlinkgoAuthenticationToken) authentication;            
        
        DecodedJWT jwt = null;
        try {
            jwt = tokenService.validateJWT((String) auth.getCredentials());          
        } catch (JWTVerificationException e) {
            throw new BadCredentialsException(e.getMessage(), e.getCause());
        }

        if (jwt == null)
            throw new BadCredentialsException("Token authenticate failed");

        String uid = tokenService.getUserFromJWT(jwt).getUserId();
        if (uid == null) {
            throw new UsernameNotFoundException("No user in token");
        }

        String userRole = jwt.getClaim("userRole").asString();
        if (userRole == null) {
            throw new InsufficientAuthenticationException("No user role in token");
        }

        List<SimpleGrantedAuthority> grantedAuth = new ArrayList<>();
        grantedAuth.add(new SimpleGrantedAuthority(userRole));

        FlinkgoAuthenticationToken newAuth;
        newAuth = new FlinkgoAuthenticationToken(tokenService.getUserFromJWT(jwt), (String) auth.getCredentials(), jwt,
                grantedAuth);

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return newAuth;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        return;
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        if (authentication instanceof FlinkgoAuthenticationToken) {

            FlinkgoAuthenticationToken auth = (FlinkgoAuthenticationToken) authentication;

            log.debug(auth.getAuthorities().toString());

            return new User((String) auth.getPrincipal(), (String) auth.getCredentials(), auth.getAuthorities());
        }
        return null;
    }
}
