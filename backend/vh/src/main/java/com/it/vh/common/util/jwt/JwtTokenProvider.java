package com.it.vh.common.util.jwt;

import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import java.net.http.HttpHeaders;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public void generateToken(Authentication authentication) {
        String authority = authentication.getAuthorities()
            .stream().map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        log.info("authority: {}", authority);

//        String accessToken = createAccessToken()
    }

    public String createAccessToken(Long userId) {
        Claims claims = Jwts.claims();
        claims.setSubject(String.valueOf(userId));

        return Jwts.builder()
            .setHeaderParam("Bearer ", "accessToken")
            .setSubject(String.valueOf(userId))
            .claim("userId", userId)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + accessToken))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String createRefreshToken(Long userId) {

        return Jwts.builder()
            .setSubject(String.valueOf(userId) + "_refresh")
            .claim("userId", userId)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + refreshToken))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    //Authentication 생성
    public Authentication getAuthentication(String token) {
        Long userId = getUserId(token);

        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            return null;
        }

        UserDetails userDetails = new CustomUserDetails(findUser.get());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public Long getUserId(String token) {
        Claims claims = parseClaims(token);

        if (claims.get("userId") == null) {
            return null;
        }

        Long userId = Long.valueOf(claims.get("userId").toString());
        return userId;
    }

    public Header parseHeader(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getHeader();
    }

    public boolean isValidateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
