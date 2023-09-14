package com.it.vh.common.util.jwt.filter;

import static com.it.vh.common.util.jwt.exception.JwtExceptionList.MALFORMED_HEADER;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.vh.common.util.jwt.JwtTokenProvider;
import com.it.vh.common.util.jwt.dto.Token;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
//    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            Token token = getToken(request);
            log.info("token: {}", token);

            if (token != null && jwtTokenProvider.isValidateToken(token.getToken())) {
                //리프레시 토큰

                //권한 설정
                Authentication authentication = jwtTokenProvider.getAuthentication(
                    token.getToken());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                Authentication authenticationCheck = SecurityContextHolder.getContext()
                    .getAuthentication();
                log.info("authenticationCheck: {}", authenticationCheck);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("[error]");
        }
    }

    public Token getToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("header: {}", header);

        //토큰 분리
        if (StringUtils.hasText(header)) {
            if (header.startsWith("Bearer")) {
                String token = header.split(" ")[1].trim();
                return Token.builder()
                    .tokenType("ACCESS")
                    .token(token)
                    .build();
            }
            throw new MalformedJwtException(MALFORMED_HEADER.getMessage());
        }

        //리프레시 토큰

        return null;
    }

}
