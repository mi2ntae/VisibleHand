package com.it.vh.user.api;

import com.it.vh.user.api.dto.auth.LoginResDto;
import com.it.vh.user.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final OAuth2UserService oauthService;

    //TODO: 구글 로그인 추가 후 {provider} 전달하는 걸로 변경
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<LoginResDto> login(@RequestParam("code") String code) {
        LoginResDto loginResDto = oauthService.login(code);
        return ResponseEntity.ok().body(loginResDto);
    }
    
    //TODO: 로그아웃

}
