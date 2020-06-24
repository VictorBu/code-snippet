package com.karonda.util;

import com.karonda.constants.JwtConstant;
import com.karonda.model.JwtModel;
import com.karonda.model.UserInfoModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET = "cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";

    private static final int TOKEN_EXPIRED_SECOND = 60;
    private static final int REFRESH_TOKEN_EXPIRED_SECOND = 70;

    public static JwtModel generateJwt(UserInfoModel userInfo) {
        SecretKey secretKey = getSecretKey();

        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        Date accessExpiredDate = new Date(currentTimeMillis + TOKEN_EXPIRED_SECOND * 1000);
        Date refreshExpiredDate = new Date(currentTimeMillis + REFRESH_TOKEN_EXPIRED_SECOND * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstant.USER_INFO_KEY, userInfo);

        // access token
        claims.put(JwtConstant.AIM_KEY, JwtConstant.AIM_ACCESS);
        String accessToken = Jwts.builder().setClaims(claims)
                .setIssuedAt(currentDate).setExpiration(accessExpiredDate)
                .signWith(secretKey)
                .compact();

        // refresh_token
        claims.put(JwtConstant.AIM_KEY, JwtConstant.AIM_REFRESH);
        String refreshToken = Jwts.builder().setClaims(claims)
                .setIssuedAt(currentDate).setExpiration(refreshExpiredDate)
                .signWith(secretKey)
                .compact();

        JwtModel jwtModel = new JwtModel();
        jwtModel.setAccessToken(accessToken);
        jwtModel.setRefreshToken(refreshToken);
        jwtModel.setExpiresIn(TOKEN_EXPIRED_SECOND);
        jwtModel.setUserInfo(userInfo);

        return jwtModel;
    }

    public static UserInfoModel verifyToken(String jwsStr, String aimFor) {

        if(jwsStr == null || jwsStr.isEmpty()) {
            return null;
        }

        try {
            SecretKey secretKey = getSecretKey();
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .deserializeJsonWith(new JacksonDeserializer(Maps.of(JwtConstant.USER_INFO_KEY, UserInfoModel.class).build()))
                    .build().parseClaimsJws(jwsStr);

            Claims claims = jws.getBody();
            String aim = (String)claims.get(JwtConstant.AIM_KEY);
            if(!aim.equals(aimFor)) {
                return null;
            }

            UserInfoModel userInfo = claims.get(JwtConstant.USER_INFO_KEY, UserInfoModel.class);

            return userInfo;
        } catch (Exception e) {
            return null;
        }
    }

    private static SecretKey getSecretKey() {
        byte[] encodeKey = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(encodeKey);
    }
}
