package main.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Bean;

public class Unsign {

    private static final String ISSUE = "YXL";

    private static final String SECRET = "011014";

    public static <T> T unsign(String token, Class<T> classT) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC384(SECRET))
                    .withIssuer(ISSUE)
                    .build();
            jwt = verifier.verify(token);
            return jwt.getHeaderClaim("USER").as(classT);
        } catch (Exception e) {
            return null;
        }
    }
}
