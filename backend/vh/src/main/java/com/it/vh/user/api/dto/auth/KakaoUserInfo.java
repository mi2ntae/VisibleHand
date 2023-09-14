package com.it.vh.user.api.dto.auth;

import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KakaoUserInfo implements AuthUserInfo {

    private Map<String, Object> attributes;
    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String) getKakaoAccount().get("email");
    }

    public Map<String, Object> getKakaoAccount() {
        return (Map<String, Object>) attributes.get("kakao_account");
    }
}
