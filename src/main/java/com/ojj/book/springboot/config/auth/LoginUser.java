package com.ojj.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 세션에서 값 꺼내올때 getAttribute("user"); 로 값을꺼내오는데
 * 각 클레스에서 코드가 반복되므로 메소드 인자로 세션값을 바로 받을 수 있게
 * 어노테이션 생성하기 위한 어노테이션 클래스
 */
@Target(ElementType.PARAMETER)  //어노테이션이 생성될 수 있는 위치 지정. 메소드의 파라미터 로 선언된 객체에서만 사용가능
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {

}
