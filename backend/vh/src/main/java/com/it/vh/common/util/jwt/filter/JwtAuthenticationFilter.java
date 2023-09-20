package com.it.vh.common.util.jwt.filter;

import static com.it.vh.common.util.jwt.exception.JwtExceptionList.MALFORMED_HEADER;
import static com.it.vh.common.util.jwt.exception.JwtExceptionList.NOT_MATCHED_TOKEN;
import static com.it.vh.common.util.jwt.exception.JwtExceptionList.TOKEN_NOTFOUND;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.vh.common.exception.ErrorResponse;
import com.it.vh.common.util.jwt.JwtTokenProvider;
import com.it.vh.common.util.jwt.dto.ReissueTokenResDto;
import com.it.vh.common.util.jwt.dto.Token;
import com.it.vh.common.util.jwt.dto.TokenInfo;
import com.it.vh.user.domain.entity.RefreshToken;
import com.it.vh.user.service.UserRedisService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final UserRedisService userRedisService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            Token token = getToken(request);

            //토큰이 존재하고 유효하면
            if (token != null && jwtTokenProvider.isValidateToken(token.getToken())) {

                //리프레시 토큰 확인
                if (token.getTokenType() == "REFRESH") {
                    Authentication authentication
                        = jwtTokenProvider.getAuthentication(token.getToken());
                    log.info("[토큰 권한 확인]: {}", authentication);

                    String userId = authentication.getName();

                    RefreshToken originrefreshToken = userRedisService.getRefreshToken(userId);

                    if (originrefreshToken == null) {
                        throw new JwtException(TOKEN_NOTFOUND.getMessage());
                    }

                    //레디스와 일치하면 만료된 accessToken 재발급 및 refreshToken 재발급
                    if (token.getToken().equals(originrefreshToken.getRefreshToken())) {
                        TokenInfo reissueToken = reissueTokensAndSaveOnRedis(authentication);

                        makeResponse(HttpStatus.CREATED.value(), response,
                            ReissueTokenResDto.of(reissueToken));
                        return;

                    } else {
                        throw new JwtException(NOT_MATCHED_TOKEN.getMessage());
                    }
                }

                Authentication authentication = jwtTokenProvider.getAuthentication(
                    token.getToken());
                log.info("[토큰 권한 확인]: {}", authentication);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("[Security 권한 등록]");

//                Authentication auth  = SecurityContextHolder.getContext().getAuthentication();
//                log.info("[권한 등록 확인] auth: {}", auth);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.info("[filter error]");
            makeResponse(HttpStatus.UNAUTHORIZED.value(), response,
                ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
        }
    }

    private Token getToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

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

    private TokenInfo reissueTokensAndSaveOnRedis(Authentication authentication) {
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        userRedisService.saveRefreshToken(authentication.getName(),
            tokenInfo.getRefreshToken());

        return tokenInfo;
    }

    private void makeResponse(int code, HttpServletResponse response, Object dto)
        throws IOException {
        response.setStatus(code);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
            objectMapper.writeValueAsString(
                dto
            )
        );
    }
}
