package com.it.vh.user.service;

import com.it.vh.common.util.CustomUserDetailsService;
import com.it.vh.common.util.JwtTokenProvider;
import com.it.vh.user.api.dto.KakaoUserInfo;
import com.it.vh.user.api.dto.LoginResDto;
import com.it.vh.user.api.dto.LoginResDto.TokenInfo;
import com.it.vh.user.api.dto.LoginResDto.UserProfile;
import com.it.vh.user.api.dto.OAuth2TokenInfo;
import com.it.vh.user.api.dto.OAuth2UserInfo;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final String type = "bearer";
    private final InMemoryClientRegistrationRepository inMemoryRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResDto login(String code) {
        ClientRegistration provider = inMemoryRepository.findByRegistrationId("kakao");
        log.info("provider: {}", provider);

        //토큰 받기
        OAuth2TokenInfo token = getToken(code, provider);
        log.info("token: {}", token);

        //가입 또는 로그인 처리
        User user = getUser(token, provider);
        log.info("user: {}", user);

        //jwt 토큰 발급
        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        UserProfile userProfile = UserProfile.builder()
            .userId(user.getUserId())
            .nickname(user.getNickname())
            .statusMsg(user.getStatusMsg())
            .profileImg(user.getProfileImg())
            .snsEmail(user.getSnsEmail())
            .build();

        TokenInfo tokenInfo = TokenInfo.builder()
            .type(type)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();

        return LoginResDto.builder()
            .user(userProfile)
            .token(tokenInfo)
            .build();
    }

    //TODO: 이미 있으면 요청 안 가도록
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

    // TODO: 프로필 이미지, 닉네임 추가 동의 받아서 프로필 설정 페이지로 넘어갈 때 미리 설정되어 있도록?
    private User getUser(OAuth2TokenInfo tokenResponse, ClientRegistration provider) {
        Map<String, Object> userAttributes = getUserAttributes(tokenResponse, provider);

        OAuth2UserInfo oAuth2UserInfo = new KakaoUserInfo(userAttributes);

        String snsEmail = oAuth2UserInfo.getEmail();
        Optional<User> findUser = userRepository.findBySnsEmail(snsEmail);

        if (findUser.isPresent()) {
            log.info(">>>>> 이미 등록된 사용자");
            return findUser.get();
        }

        // TODO: 권한 추가?
        User saveUser = User.builder()
            .nickname("test")
            .profileImg("test")
            .statusMsg("test")
            .snsEmail(snsEmail)
            .build();
        userRepository.save(saveUser);

        return saveUser;
    }

    private Map<String, Object> getUserAttributes(OAuth2TokenInfo tokenResponse,
        ClientRegistration provider) {
        return WebClient.create()
            .get()
            .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
            .headers(header -> {
                header.setBearerAuth(tokenResponse.getAccess_token());
            })
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
            })
            .block();
    }

}
