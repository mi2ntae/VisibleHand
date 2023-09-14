package com.it.vh.common.util.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.vh.common.exception.ErrorResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import static com.it.vh.common.util.jwt.exception.JwtExceptionList.ACCESS_DENIED;

//액세스할 수 없는 요청을 했을 때
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.info("accessDenied");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
            objectMapper.writeValueAsString(
                ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), ACCESS_DENIED.getMessage())
            )
        );
    }
}
