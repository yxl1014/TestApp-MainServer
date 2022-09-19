package main.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.protobuf.InvalidProtocolBufferException;
import main.pto.TestProto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * token加密解密类
 */
public class JWTUtil {

    private static final String ISSUE = "YXL";

    private static final String SECRET = "011014";


    //加密，传入一个对象和有效期
    public static <T> String sign(T object, long maxAge) {
        Map<String, Object> claims = new HashMap<>();
        long l = System.currentTimeMillis();
        claims.put("USER", object);

        return JWT.create()
                .withIssuer(ISSUE)
                .withIssuedAt(new Date(l))
                .withHeader(claims)
                .withExpiresAt(new Date(l + maxAge))
                .sign(Algorithm.HMAC384(SECRET));
    }


    //解密，传入一个加密后的token字符串和解密后的类型
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

    public static void main(String[] args) throws InvalidProtocolBufferException, InterruptedException {
        TestProto.User.Builder builder = TestProto.User.newBuilder();
        byte[] bytes = builder.setUserPassword("123456").build().toByteArray();

        String token = JWTUtil.sign(bytes, 3000);

        byte[] data = JWTUtil.unsign(token, byte[].class);
        TestProto.User user = TestProto.User.parseFrom(data);
        System.out.println(user.getUserPassword());
        Thread.sleep(5000);
        byte[] data2 = JWTUtil.unsign(token, byte[].class);
        if (data2 == null) {
            return;
        }
        TestProto.User user1 = TestProto.User.parseFrom(data2);
        System.out.println(user1.getUserPassword());

    }

}
