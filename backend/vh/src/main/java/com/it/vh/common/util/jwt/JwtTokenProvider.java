package com.it.vh.common.util.jwt;

import static com.it.vh.common.util.jwt.exception.JwtExceptionList.EXPIRED_TOKEN;
import static com.it.vh.common.util.jwt.exception.JwtExceptionList.INVALID_TOKEN;

import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    @Value("${jwt.access-expired-in}")
    private Long accessToken;
    @Value("${jwt.refresh-expired-in}")
    private Long refreshToken;

    private UserRespository userRepository;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

//    public TokenInfo generateToken(Authentication authentication) {
//        Jwts.builder()
//            .setSubject(String.valueOf(userId))
//            .claim("userId", userId)
//            .setIssuedAt(new Date(System.currentTimeMillis()))
//            .setExpiration(new Date(System.currentTimeMillis() + accessToken))
//            .signWith(key, SignatureAlgorithm.HS256)
//            .compact();
//    }
    public String createAccessToken(Long userId) {
//        Claims claims = Jwts.claims();
//        claims.setSubject(String.valueOf(userId));

        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .claim("userId", userId)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + accessToken))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String createRefreshToken(Long userId) {
        return Jwts.builder()
            .setSubject(String.valueOf(userId) + "_refresh")
            .claim("userId", userId)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + refreshToken))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Long userId = getUserId(token);

        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            return null;
        }

        UserDetails userDetails = new CustomUserDetails(findUser.get());
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    public Claims parseClaims(String token) {
        Claims claims = null;
        try {
            claims
                = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(parseHeader(token), claims, EXPIRED_TOKEN.getMessage());
        }
    }

    private Long getUserId(String token) {
        Claims claims = parseClaims(token);

        if (claims.get("userId") == null) {
            return null;
        }

        Long userId = Long.valueOf(claims.get("userId").toString());
        return userId;
    }

    private Header parseHeader(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getHeader();
    }

    public boolean isValidateToken(String token) throws JwtException {
        try {
            Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
            return true;
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            throw new SignatureException(INVALID_TOKEN.getMessage());
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(parseHeader(token), parseClaims(token),
                EXPIRED_TOKEN.getMessage());
        }
    }
}
