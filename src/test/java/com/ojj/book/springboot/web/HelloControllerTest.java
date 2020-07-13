package com.ojj.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class) //스프링 부트 내부의 실행자와 Junit 사이 연결 역활
@WebMvcTest(controllers = HelloController.class) //Controller, ControllerAdvice 를 테스트할때 사용
public class HelloControllerTest {

    @Autowired // Bean 자동 주입
    private MockMvc mvc;   //web api를 테스트할때 가상으로 실행할수 있게 해줌

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) //  "/hello"로 get 방식 요청
                .andExpect(status().isOk())  // HTTP Header 의 status 를 검증
                .andExpect(content().string(hello));  // 응답 내용이 'hello' 와 같은지 검증.
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto").param("name",name).param("amount",String.valueOf(amount))) // 파라미터 넘길때 값은 String 만 가능
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))  //json 응답값을 필드별로 검증할 수 있는 메소드 $.필드명
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}
