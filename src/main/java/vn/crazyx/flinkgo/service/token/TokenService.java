package vn.crazyx.flinkgo.service.token;

import com.auth0.jwt.interfaces.DecodedJWT;

import vn.crazyx.flinkgo.config.AuthUser;
import vn.crazyx.flinkgo.dao.User;

public interface TokenService {
    public String genJWT(User user, long currentTime);
    
    public DecodedJWT validateJWT(String token);

    AuthUser getUserFromJWT(DecodedJWT token);
}
