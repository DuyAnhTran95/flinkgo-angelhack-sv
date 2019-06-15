package vn.crazyx.flinkgo.config.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.auth0.jwt.interfaces.DecodedJWT;

import vn.crazyx.flinkgo.config.AuthUser;

@SuppressWarnings("serial")
public class FlinkgoAuthenticationToken extends UsernamePasswordAuthenticationToken {
    
    private DecodedJWT accessJWT;
    
    protected String refreshToken;

    public FlinkgoAuthenticationToken(AuthUser principal, String credentials) {
        super(principal, credentials);
    }
    
    public FlinkgoAuthenticationToken(AuthUser principal, String credentials, DecodedJWT accessJWT,
            Collection<? extends GrantedAuthority> grantedAuth) {
        super(principal, credentials, grantedAuth);
        this.accessJWT = accessJWT;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public DecodedJWT getAccessJWT() {
        return accessJWT;
    }
}
