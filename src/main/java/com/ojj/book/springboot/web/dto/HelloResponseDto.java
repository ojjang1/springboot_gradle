package com.ojj.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter  // 필드에 자동 get 메서드 생성
@RequiredArgsConstructor  // 선언된 모든 final 필드가 포함된 생성자를 생성 final 이 없는 필드는 생성자에 포함 안됨
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
