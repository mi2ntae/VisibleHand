package com.it.vh.common.util.jwt;

import static com.it.vh.common.util.jwt.exception.JwtExceptionList.EXPIRED_TOKEN;
import static com.it.vh.common.util.jwt.exception.JwtExceptionList.INVALID_TOKEN;

import com.it.vh.common.util.jwt.dto.Token;
import com.it.vh.user.api.dto.auth.LoginResDto.TokenInfo;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import com.sun.security.auth.UnixNumericGroupPrincipal;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    @Value("${jwt.access-expiration}")
    private Long access_expiration;
    @Value("${jwt.refresh-expiration}")
    private Long refresh_expiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenInfo generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
        log.info("authorities: {}", authorities);
        log.info("userId: {}", authentication.getName());

        String accessToken = Jwts.builder()
            .setSubject(String.valueOf(authentication.getName()))
            .claim("userId", authentication.getName())
            .claim("authority", authorities)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + access_expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        String refreshToken = Jwts.builder()
            .setSubject(String.valueOf(authentication.getName()) + "_refresh")
            .claim("userId", authentication.getName())
            .claim("authority", authorities)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + refresh_expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        return TokenInfo.builder()
                .type("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
//    public String createAccessToken(Long userId) {
//        return Jwts.builder()
//            .setSubject(String.valueOf(userId))
//            .claim("userId", userId)
//            .claim("auth", userId)
//            .setIssuedAt(new Date(System.currentTimeMillis()))
//            .setExpiration(new Date(System.currentTimeMillis() + access_expiration))
//            .signWith(key, SignatureAlgorithm.HS256)
//            .compact();
//    }
//
//    public String createRefreshToken(Long userId) {
//        return Jwts.builder()
//            .setSubject(String.valueOf(userId) + "_refresh")
//            .claim("userId", userId)
//            .setIssuedAt(new Date(System.currentTimeMillis()))
//            .setExpiration(new Date(System.currentTimeMillis() + refresh_expiration))
//            .signWith(key, SignatureAlgorithm.HS256)
//            .compact();
//    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        Collection<? extends GrantedAuthority> authorities
            = Arrays.stream(claims.get("auth").toString()
                .split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        org.springframework.security.core.userdetails.User user
            = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);

//        Long userId = getUserId(token);
//        log.info("userId: {}", userId);
//
//        Optional<User> findUser = userRepository.findById(userId);
//        if (findUser.isEmpty()) {
//            return null;
//        }
//        UserDetails userDetails = new CustomUserDetails(findUser.get());
//        log.info("userDetails: {}", userDetails);
//        return new UsernamePasswordAuthenticationToken(userDetails, token,
//            userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(user, token, authorities);
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

    public Long getUserId(String token) {
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
