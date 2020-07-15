package com.ojj.book.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @EnableJpaAuditing //JPA Auditing 어노테이션들을 사용할 수 있게 활성화(security test를 하기위해 삭제됨)-JpaConfig 클래스로 기능이동
@SpringBootApplication //스프링부트 자동설정, 스프링 Bean읽기 와 생성 모두 자동
public class Application {
    public static void main(String[] args) {
        // 이 클래스가 프로젝트의 메인 클레스이고 @SpringBootApplication 이 있는 위치부터 읽기때문에
        // 이 클래스가 항상 프로젝트의 최상단에 위치하게 한다.
        SpringApplication.run(Application.class, args);
    }

}
