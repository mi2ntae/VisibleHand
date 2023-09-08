package com.it.vh.user.api.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {

    Map<String, Object> attributes;
    private String nameAttributeKey;
    private String SnsEmail;

    //TODO: 사용자가 닉네임, 프로필 이미지 제공한 경우에 그걸 기본 설정하도록 변경하기

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if(registrationId.equals("kakao")) {
            return ofKakao("id", attributes);
        } else if(registrationId.equals("google")) {
            return ofGoogle("sub", attributes);
        }
        return null;
    }

    //TODO: 구글 로그인
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return null;
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");

        return OAuthAttributes.builder()
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .SnsEmail((String) kakao_account.get("email"))
            .build();
    }
}
