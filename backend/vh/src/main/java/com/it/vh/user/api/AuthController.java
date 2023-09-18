package com.it.vh.user.api;

import com.it.vh.user.api.dto.auth.LoginResDto;
import com.it.vh.user.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthUserService oauthService;

    //카카오 로그인
    //https://kauth.kakao.com/oauth/authorize?response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogin%2Foauth2%2Fcode%2Fkakao&through_account=true&client_id=239963a7ee5248741ccce5709bed01cf&additional_auth_login=true
    //구글 로그인
    //https://accounts.google.com/o/oauth2/v2/auth?client_id=627892398327-lne8q8lf9jkbju5glga1vm9q03vktpjr.apps.googleusercontent.com&redirect_uri=http://localhost:8080/login/oauth2/code/google&response_type=code&scope=email
    @GetMapping("/login/oauth2/code/{provider}")
    public ResponseEntity<LoginResDto> login(@PathVariable(name = "provider") String provider,
        @RequestParam(name = "code") String code) {
        LoginResDto loginResDto = oauthService.login(code, provider);
        return ResponseEntity.ok().body(loginResDto);
    }
}
