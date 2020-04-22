package com.karonda.jjwtdemo;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {
    /**
     * SECRET 是签名密钥，只生成一次即可，生成方法：
     * Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
     * String secretString = Encoders.BASE64.encode(key.getEncoded()); # 本文使用 BASE64 编码
     * */
    private static final String SECRET = "cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";

    private static final String USER_INFO_KEY = "user_info";

    private static final long TOKEN_EXPIRED_SECOND = 60;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/get")
    public Object get() {

        Map<String, Object> claims = new HashMap<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setId("123456");
        userInfo.setName("测试abc123");
        claims.put(USER_INFO_KEY, userInfo);

        // 添加自定义参数
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims);

        long currentTimeMillis = System.currentTimeMillis();
        // 设置过期时间
        jwtBuilder.setExpiration(new Date(currentTimeMillis + TOKEN_EXPIRED_SECOND * 1000));

        SecretKey secretKey = getSecretKey();
        jwtBuilder.signWith(secretKey);

        String jwsStr = jwtBuilder.compact();
        return jwsStr;
    }

    @RequestMapping("/verify")
    public Object verify() {
        String token = request.getHeader("Authorization");

        SecretKey secretKey = getSecretKey();
        Jws<Claims> jws = Jwts.parserBuilder()
                // 解析 JWT 的服务器与创建 JWT 的服务器的时钟不一定完全同步，此设置允许两台服务器最多有 3 分钟的时差
                .setAllowedClockSkewSeconds(180L)
                .setSigningKey(secretKey)
                // 默认情况下 JJWT 只能解析 String, Date, Long, Integer, Short and Byte 类型，如果需要解析其他类型则需要配置 JacksonDeserializer
                .deserializeJsonWith(new JacksonDeserializer(Maps.of(USER_INFO_KEY, UserInfo.class).build()))
                .build().parseClaimsJws(token);

        Claims claims = jws.getBody();

        UserInfo userInfo = claims.get(USER_INFO_KEY, UserInfo.class);

        return userInfo;
    }

    /**
     * SecretKey 根据 SECRET 的编码方式解码后得到：
     * Base64 编码：SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
     * Base64URL 编码：SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretString));
     * 未编码：SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
     * */
    private SecretKey getSecretKey() {
        byte[] encodeKey = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(encodeKey);
    }
}
