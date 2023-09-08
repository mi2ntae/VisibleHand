package com.it.vh.user.service;

import com.it.vh.user.api.dto.OAuthAttributes;
import com.it.vh.user.domain.entity.User;
import com.it.vh.user.domain.repository.UserRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    //TODO: jwt 토큰
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);
        log.info("oAuth2User: {}", oAuth2User);

        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        log.info("registrationId: {}", registrationId);
        log.info("userNameAttributeName: {}", userNameAttributeName);

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        log.info("oAuthAttributes: {}", oAuthAttributes);

        User user = User.builder().nickname("test").profileImg("").snsEmail(oAuthAttributes.getSnsEmail()).build();
        User saveUser = saveUser(user);

        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
    }

    public User saveUser(User user) {
        Optional<User> selectUser = userRepository.findBySnsEmail(user.getSnsEmail());

        if(selectUser.isEmpty()) {
            return userRepository.save(user);
        } else {
            return selectUser.get();
        }
    }
}
