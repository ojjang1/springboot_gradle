package com.ojj.book.springboot.web;

import com.ojj.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController  // JSON 으로 반환하는 컨트롤러
public class HelloController {

    @GetMapping("/hello")
    public String Hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, //web에서 넘어온 파라미터 바로 받음
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }

}
