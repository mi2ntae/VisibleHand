package com.it.vh.user.api;

import com.it.vh.user.api.dto.auth.LoginResDto;
import com.it.vh.user.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class AuthController {

    private final AuthUserService oauthService;

    @GetMapping("/auth/{provider}")
    public ResponseEntity<LoginResDto> login(@PathVariable(name = "provider") String provider,
        @RequestParam(name = "code") String code) {
        LoginResDto loginResDto = oauthService.login(code, provider);
        return ResponseEntity.ok().body(loginResDto);
    }
}
