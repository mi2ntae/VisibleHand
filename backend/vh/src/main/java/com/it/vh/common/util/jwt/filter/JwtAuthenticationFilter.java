package com.it.vh.common.util.jwt.filter;

import static com.it.vh.common.util.jwt.exception.JwtExceptionList.ACCESS_DENIED;
import static com.it.vh.common.util.jwt.exception.JwtExceptionList.MALFORMED_HEADER;
import static com.it.vh.common.util.jwt.exception.JwtExceptionList.TOKEN_NOTFOUND;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.vh.common.exception.ErrorResponse;
import com.it.vh.common.util.jwt.CustomUserDetails;
import com.it.vh.common.util.jwt.JwtTokenProvider;
import com.it.vh.common.util.jwt.dto.Token;
import com.it.vh.user.api.dto.auth.LoginResDto.TokenInfo;
import com.it.vh.user.domain.entity.RefreshToken;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import com.it.vh.user.exception.NonExistUserIdException;
import com.it.vh.user.service.UserRedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final UserRespository userRespository;
//    private final UserRedisService userRedisService;
    //    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            Token token = getToken(request);
            log.info("token: {}", token);

            //토큰이 존재하고 유효하면
            if (token != null && jwtTokenProvider.isValidateToken(token.getToken())) {

                Authentication authentication = jwtTokenProvider.getAuthentication(token.getToken());
                log.info("authentication: {}", authentication);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("등록");
//                Authentication auth = jwtTokenProvider.getAuthentication(token.getToken());
//                log.info("auth: {}", auth);
//                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                log.info("auth: {}", auth);

//                Long num = Long.parseLong(auth.getName());
//                User findUser = userRespository.findById(num).orElseThrow();
//
//                //권한 설정
//                UserDetails userDetails = new CustomUserDetails(findUser);
//                UsernamePasswordAuthenticationToken authenticationToken
//                    = new UsernamePasswordAuthenticationToken(userDetails, "",
//                    userDetails.getAuthorities());
//                log.info("authenticationToken: {}", authenticationToken);
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

//                Authentication authentication = jwtTokenProvider.getAuthentication(
//                    token.getToken());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                Authentication authenticationCheck = SecurityContextHolder.getContext()
//                    .getAuthentication();
//                log.info("authenticationCheck: {}", authenticationCheck);

//                Claims claims = jwtTokenProvider.parseClaims(token.getToken());
//                log.info("clamin: {}", claims);
//                log.info("auth: {}", claims.get("auth"));
//                Long userId = jwtTokenProvider.getUserId(token.getToken());
//                log.info("userId: {}", userId);
//                User findUser = userRespository.findById(userId).orElseThrow(
//                    () -> new NonExistUserIdException()
//                );
//                UserDetails userDetails = new CustomUserDetails(findUser);
//                UsernamePasswordAuthenticationToken authenticationToken
//                    = new UsernamePasswordAuthenticationToken(userDetails, "",
//                    userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//                Authentication authenticationCheck = SecurityContextHolder.getContext()
//                    .getAuthentication();




                //리프레시 토큰
//                if (token.getTokenType() == "REFRESH") {
//                    String refreshToken = token.getToken();
//                    Authentication authentication
//                        = jwtTokenProvider.getAuthentication(refreshToken);
//
//                    String userId = authentication.getName();
//                    RefreshToken originrefreshToken = userRedisService.getRefreshToken(userId)
//
//                    if(originrefreshToken==null) {
//                        throw new JwtException(TOKEN_NOTFOUND.getMessage());
//                    }
//
//                    if(refreshToken.equals(originrefreshToken.getRefreshToken())) {
//                        TokenInfo reissueToken = reissueTokensAndSaveOnRedis(authentication);
//                    }
//                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(
                objectMapper.writeValueAsString(
                    ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), e.getMessage())
                )
            );
        }
    }

    public Token getToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("token: {}", token);

        //토큰 분리
        if (StringUtils.hasText(token)) {
            if (token.startsWith("Bearer")) {
                String accessToken = token.split(" ")[1].trim();
                return Token.builder()
                    .tokenType("ACCESS")
                    .token(accessToken)
                    .build();
            }
            throw new MalformedJwtException(MALFORMED_HEADER.getMessage());
        }

        //리프레시 토큰
        token = request.getHeader("refreshToken");
        if (StringUtils.hasText(token)) {
            return Token.builder()
                .tokenType("REFRESH")
                .token(token)
                .build();
        }

        return null;
    }

//    private TokenInfo reissueTokensAndSaveOnRedis(Authentication authentication) {
//        TokenInfo reissueToken
//            = jwtTokenProvider.createAccessToken()
//    }

}
