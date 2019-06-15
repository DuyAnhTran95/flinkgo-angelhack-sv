package vn.crazyx.flinkgo.service.token;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import vn.crazyx.flinkgo.config.AuthUser;
import vn.crazyx.flinkgo.dao.User;

@Service
public class JWTTokenService implements TokenService {
    private static Logger log = LoggerFactory.getLogger("JWT");
        
    public static final long EXPIRE_TIME = 1800;
    
    private static final String FLINKGO = "flinkgo";
    private static final String UID = "uid";
    private static final String USERNAME = "user";

    private static final long LEEWAY = 2;
    
    @Value("${rsa.key.path}")
    private String keyFolder;
    
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    
    @PostConstruct
    protected void initKeys() throws InvalidKeySpecException, NoSuchAlgorithmException, 
            IOException, URISyntaxException {
        log = LoggerFactory.getLogger("JWT");
        log.warn("init rsa keys: " + keyFolder);
        
        byte[] publicBytes = Files.readAllBytes(Paths.get(keyFolder + "/public_key.der"));
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        
        byte[] privateByte = Files.readAllBytes(Paths.get(keyFolder + "/private_key.der"));
        PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(privateByte);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpecPrivate);
        
        this.publicKey = (RSAPublicKey) pubKey;
        this.privateKey = (RSAPrivateKey) privateKey;
    }
    
    @Override
    public String genJWT(User user, long currentTime) {
        Algorithm algo = Algorithm.RSA256(publicKey, privateKey);
        
        Builder tokenBuilder = JWT.create()
                .withIssuer(FLINKGO)
                .withIssuedAt(new Date(currentTime))
                .withExpiresAt(new Date(currentTime + EXPIRE_TIME * 1000))
                .withClaim(UID, user.getId())
                .withClaim(USERNAME, user.getUserName())
                .withClaim("userRole", "user");
                    
        return tokenBuilder.sign(algo);
    }
    
    @Override
    public DecodedJWT validateJWT(String token) throws JWTVerificationException {
        DecodedJWT jwt = null;
        try {
            Algorithm algo = Algorithm.RSA256(publicKey, privateKey);
          
            JWTVerifier verifier = JWT.require(algo)
                    .withIssuer(FLINKGO)
                    .acceptLeeway(LEEWAY)
                    .build();
            
            jwt = verifier.verify(token);
        } catch (JWTCreationException e) {
            e.printStackTrace();
            jwt = null;
        }
        
        return jwt;
    }

    @Override
    public AuthUser getUserFromJWT(DecodedJWT token) {
        
        log.debug(token.getClaim("user").asString());
        return new AuthUser(token.getClaim(UID).asString(), token.getClaim(USERNAME).asString());
    }
   
}
