package com.it.vh.user.service;

import com.it.vh.common.util.jwt.CustomUserDetails;
import com.it.vh.common.util.jwt.JwtTokenProvider;
import com.it.vh.user.api.dto.auth.KakaoUserInfo;
import com.it.vh.user.api.dto.auth.LoginResDto;
import com.it.vh.user.api.dto.auth.LoginResDto.TokenInfo;
import com.it.vh.user.api.dto.auth.LoginResDto.UserProfile;
import com.it.vh.user.api.dto.auth.OAuth2TokenInfo;
import com.it.vh.user.api.dto.auth.OAuth2UserInfo;
import com.it.vh.user.domain.dto.UserDto;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRespository;
import io.jsonwebtoken.Header;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OAuth2UserService {

    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final UserRespository userRespository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public LoginResDto login(String code) {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId("kakao");

        //토큰 받기
        OAuth2TokenInfo token = getToken(code, provider);

        //가입 또는 로그인 처리
        User user = getUser2(token, provider);
        log.info("user: {}", user);

        UserProfile userProfile = UserProfile.builder()
            .userId(user.getUserId())
            .nickname(user.getNickname())
            .statusMsg(user.getStatusMsg())
            .profileImg(user.getProfileImg())
            .snsEmail(user.getSnsEmail())
            .build();

        //jwt 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        TokenInfo tokenInfo = TokenInfo.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        log.info("-----------------------권한 확인 시작");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("등록 전 auth: {}", auth);

        UserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());

        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
        log.info("등록 후 auth: {}", auth2);

        Long num = Long.parseLong(auth2.getName());
        log.info("userId 확인: {}", num);
        log.info("권한 확인 끝-----------------------");

        Header header = jwtTokenProvider.parseHeader(accessToken);
        Header header2 = jwtTokenProvider.parseHeader(refreshToken);
        log.info("header: {}", header);
        log.info("header2: {}", header2);

        //-------------------------------------------------
        //일단은 DTO로 가지고 있다가 프로필 작성 후 가입 또는 로그인 처리
//        UserDto userDto = getUser(token, provider);
//        log.info("userId: {}", userDto.getUserId());

//        UserProfile userProfile = UserProfile.builder()
//            .userId(userDto.getUserId())
//            .nickname(userDto.getNickname())
//            .statusMsg(userDto.getStatusMsg())
//            .profileImg(userDto.getProfileImg())
//            .snsEmail(userDto.getSnsEmail())
//            .build();

//        //하지만 이미 가입된 회원은
//        //로그인 시 jwt 토큰 발급
//        //근데 일단은 이 주소로 요청 들어오면 다 토큰 발급되도록 해놓음
//        if (userDto.getUserId() != null) {
//            String accessToken = jwtTokenProvider.createAccessToken(userDto.getUserId());
//            String refreshToken = jwtTokenProvider.createRefreshToken(userDto.getUserId());
//
//            String accessToken = jwtTokenProvider.createAccessToken(userDto.getUserId());
//            String refreshToken = jwtTokenProvider.createRefreshToken(userDto.getUserId());
//
//            TokenInfo tokenInfo = TokenInfo.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
//
//            return LoginResDto.builder()
//                .user(userProfile)
//                .token(tokenInfo)
//                .build();
//        }

        return LoginResDto.builder()
            .user(userProfile)
            .token(tokenInfo)
            .build();
    }

    private OAuth2TokenInfo getToken(String code, ClientRegistration provider) {
        return WebClient.create()
            .post()
            .uri(provider.getProviderDetails().getTokenUri())
            .headers(header -> {
                header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
            })
            .bodyValue(tokenRequest(code, provider))
            .retrieve()
            .bodyToMono(OAuth2TokenInfo.class)
            .block();
    }

    private MultiValueMap<String, String> tokenRequest(String code, ClientRegistration provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", provider.getClientId());
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("code", code);
        formData.add("client_secret", provider.getClientSecret());
        return formData;
    }

    private UserDto getUser(OAuth2TokenInfo token, ClientRegistration provider) {
        Map<String, Object> userAttributes = getUserAttributes(token, provider);

        OAuth2UserInfo oAuth2UserInfo = new KakaoUserInfo(userAttributes);
        String snsEmail = oAuth2UserInfo.getEmail();
        Optional<User> findUser = userRespository.findBySnsEmail(snsEmail);

        if (findUser.isPresent()) {
            log.info(">>>>> 이미 등록된 사용자");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("auth: {}", auth.getName());
            return UserDto.from(findUser.get());
        }

        UserDto userDto = UserDto.builder().snsEmail(snsEmail).build();
        return userDto;
    }

    private User getUser2(OAuth2TokenInfo token, ClientRegistration provider) {
        Map<String, Object> userAttributes = getUserAttributes(token, provider);

        OAuth2UserInfo oAuth2UserInfo = new KakaoUserInfo(userAttributes);
        String snsEmail = oAuth2UserInfo.getEmail();

        User saveUser = User.builder()
            .nickname("testNick")
            .profileImg("testImg")
            .statusMsg("testMsg")
            .snsEmail(snsEmail)
            .build();
        userRespository.save(saveUser);

        return saveUser;
    }

    private Map<String, Object> getUserAttributes(OAuth2TokenInfo token,
        ClientRegistration provider) {
        return WebClient.create()
            .get()
            .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
            .headers(header -> {
                header.setBearerAuth(token.getAccess_token());
            })
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
            })
            .block();
    }

//    public void logout(Long userId) {
//        //accessToken 불러오기
//        String accessToken = jwtTokenProvider.getAccessToken(userId);
//
//       WebClient.ResponseSpec res = WebClient.create()
//            .post()
//            .uri("https://kapi.kakao.com/v1/user/logout")
//            .headers(header -> {
//               header.setBearerAuth(accessToken);
//            })
//           .retrieve();
//    }

    //탈퇴 시 토큰도 만료돼서 다시 동의항목 처리하도록
}
