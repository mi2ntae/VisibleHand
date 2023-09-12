package com.it.vh.common.util;

import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-expired-in}")
    private Long accessToken;
    @Value("${jwt.refresh-expired-in}")
    private Long refreshToken;

    private final UserRespository userRepository;

    public String createAccessToken(Long userId) {
//        Claims claims = Jwts.claims();
//        claims.setSubject(String.valueOf(userId));

        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .claim("userId", userId)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + accessToken))
            .signWith(SignatureAlgorithm.HS384, secretKey)
            .compact();
    }

    public String createRefreshToken(Long userId) {
        return Jwts.builder()
            .setSubject(String.valueOf(userId) + "_refresh")
            .claim("userId", userId)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + refreshToken))
            .signWith(SignatureAlgorithm.HS384, secretKey)
            .compact();
    }

    //Authentication 생성
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        if (claims.get("userId") == null) {
            return null;
        }

        Long userId = Long.valueOf(claims.get("userId").toString());
        log.info("userId: {}", userId);

        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            return null;
        }

        return null;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
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

//    public Long getUserId(String token) {
//        return Jwts.parser()
//            .setSigningKey(secretKey)
//            .parseClaimsJws(token)
//            .getBody()
//            .get("userId", Long.class);
//    }
}
