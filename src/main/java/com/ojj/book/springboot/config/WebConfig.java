package com.ojj.book.springboot.config;

import com.ojj.book.springboot.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * LoginUserArgumentResonver 가 스프링에서 인식될수있게 설정해주는 클래스
 * 이거 느낌이 webConfig.xml 파일 을 Java 클래스로 옮기는 형태랑 비슷한듯?
 * HandlerMethodArgumentResolver 는 항상 WebMvcConfigurer 의 addArgumentResolvers() 를 통해 추가해야함
 * 다른 HandlerMethodArgumentResolver 가 필요하다면 같은 방식으로 추가할 수 있다.
 */
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolverer;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolverer);
    }
}
