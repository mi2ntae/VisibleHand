package com.it.vh.user.api;

import com.it.vh.user.api.dto.LoginResDto;
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

    //카카오 로그인 요청 후 인가 코드 받기
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<LoginResDto> login(@RequestParam("code") String code) {
        LoginResDto loginResDto = oauthService.login(code);
        return ResponseEntity.ok().body(loginResDto);
    }

    //TODO
    // 닉네임 중복 검사
    // 프로필 작성
    // 프로필 수정
    // 회원 탈퇴

}
