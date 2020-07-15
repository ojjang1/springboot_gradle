package com.ojj.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // 스프링 시큐리티를 사용한 정보 보관 클래스 enum
    // 권한 코드에 ROLE_ 가 필수로 붙어야함.
    GUEST("ROLE_GUEST", "손님"), USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}
