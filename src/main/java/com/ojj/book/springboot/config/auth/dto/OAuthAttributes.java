package com.ojj.book.springboot.config.auth.dto;

import com.ojj.book.springboot.domain.user.Role;
import com.ojj.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 *  OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담는 클래스
 *  구글/네이버 모두 이 클래스 사용할 예정
 */
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        // 네이버를 추가하여서 등록환경이 naver.인지 확인
        if("naver".equals(registrationId)) {
            System.out.println("네이버로 로그인");
            return ofNaver("id", attributes);
        }
        System.out.println("구글로 로그인");
        return ofGoogle(userNameAttributeName, attributes);
    }

    /**
     * OAuth2User 에서 반환하는 사용자 정보는 Map이기 때문에 하나하나씩 변환해서 빌드함
     * @param userNameAttributeName
     * @param attributes
     * @return
     */
    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                           Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * naver 추가
     */
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        // naver 에서는 결과가 JSON 형태로 들어오는데 username 가 response 필드 안에 있어서 이것을 받아온다.
        // JSON 의 최 상위 필드만 지정 가능하기 때문에 response에서 안에 내용을 꺼내는 방법을 사용
        Map<String, Object> response = (Map<String,Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }



    /**
     * User 엔티티 생서
     * OAuthAttributes 에서 엔티티를 생성하는 시점은 처음 가입할 때이다.
     * 가입할 때 기본 권한을 GUEST로 주기 위해서 role 빌더값에 Role.GUEST를 사용
     * OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스도 생성
     * @return
     */
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }


}
