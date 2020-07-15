package com.ojj.book.springboot.config.auth;

import com.ojj.book.springboot.config.auth.dto.OAuthAttributes;
import com.ojj.book.springboot.config.auth.dto.SessionUser;
import com.ojj.book.springboot.domain.user.User;
import com.ojj.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 *  구글 로그인 이후 가져온 사용자의 정보(email, name, picture등)들을 기반으로
 *  가입, 및 정보수정, 세션 저장 등의 기능을 지원
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 현재 로그인 진행중인 서비스를 구분하는 코드
        // 구글인지, 네이버인지 구분하기 위해 사용

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        //Oauth2 로그인 진행 시 키가 되는 필드값 (PK 와 같은 의미)
        //구글은 기본제공 기본코드는 "sub", 네이버,카카오 는 기본지원하지않음 .
        // 네이버 로그인과 구글 로그인을 동시 지원할때 사용


        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                                      attributes.getAttributes(), attributes.getNameAttributeKey());

    }// public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {} END

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}