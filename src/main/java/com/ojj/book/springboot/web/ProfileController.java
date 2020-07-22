package com.ojj.book.springboot.web;


import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        /** 현재 실행중인 ActivePrifile을 모두 가져옴
         * 즉, real, oauth, real-db 등이 활성화 되어있으면(active) 3개가 모두 담겨있음
         *  여기서 real, real1, real2는 모두 배포에 사용될 profile이라 이중 하나라도 있으면 그 값을 반환
         *  실제로 무중단 배포에서 real1, real2만 사용되지만 이전 환경 다시 쓸수도 있으니 real도 남겨둠
         */
        List<String> profiles = Arrays.asList(env.getActiveProfiles());

        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);
        String result = profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
        System.out.println("조회된 profile : " + result);
        return  result;

    }
}
