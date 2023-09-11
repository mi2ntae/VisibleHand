package com.it.vh.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-expired-in}")
    private Long accessToken;
    @Value("${jwt.refresh-expired-in}")
    private Long refreshToken;

    public String createAccessToken(Long userId) {
        Claims claims = Jwts.claims();
        claims.put("user_role", "ROLE_USER");
        claims.put("userId", userId);

        return Jwts.builder()
            .setHeaderParam("type", "accessToken")
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + accessToken))
            .signWith(SignatureAlgorithm.HS384, secretKey)
            .compact();
    }

    public String createRefreshToken(Long userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
            .setHeaderParam("type", "refreshToken")
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + accessToken))
            .signWith(SignatureAlgorithm.HS384, secretKey)
            .compact();
    }

    public boolean isValidateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .get("userId", Long.class);
    }
}
