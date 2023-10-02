package com.it.vh.user.api;

import com.it.vh.common.util.jwt.dto.TokenInfo;
import com.it.vh.user.api.dto.auth.LoginResDto;
import com.it.vh.user.service.AuthUserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class AuthController {

    private final AuthUserService oauthService;

    @ApiOperation(value = "유저 소셜 로그인", notes = "유저가 가입 또는 로그인합니다.")
    @GetMapping("/auth/{provider}")
    public ResponseEntity<LoginResDto> login(@PathVariable(name = "provider") String provider,
                                             @RequestParam(name = "code") String code) {
        LoginResDto loginResDto = oauthService.login(code, provider);
        return ResponseEntity.ok().body(loginResDto);
    }

    @ApiOperation(value = "유저 토큰 재발급", notes = "유저의 토큰이 만료된 경우 확인 후 재빌급합니다.")
    @GetMapping("/auth/token/{userId}")
    public ResponseEntity<TokenInfo> setToken(@PathVariable Long userId) {
        TokenInfo token = oauthService.setToken(userId);
        return ResponseEntity.ok().body(token);
    }
}
