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

    //https://kauth.kakao.com/oauth/authorize?response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogin%2Foauth2%2Fcode%2Fkakao&through_account=true&client_id=239963a7ee5248741ccce5709bed01cf&additional_auth_login=true
    //TODO: 구글 로그인 추가 후 {provider} 전달하는 걸로 변경
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<LoginResDto> login(@RequestParam("code") String code) {
        LoginResDto loginResDto = oauthService.login(code);

        return ResponseEntity.ok().body(loginResDto);
    }
    
//    @GetMapping("/logout/kakao/{userId}")
//    public ResponseEntity<String> logout(@PathVariable Long userId) {
//        oauthService.logout(userId);
//        return ResponseEntity.ok().build();
//    }
}
