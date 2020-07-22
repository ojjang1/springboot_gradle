package com.ojj.book.springboot.config.auth;

import com.ojj.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  //  Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들을 disable 함
                .and()
                    .authorizeRequests() //URL별 권한 관리를 설정하는 옵션의 시작점입니다.
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile" ).permitAll()// 모든 사용자 접근 가능
                    // 권한 관리 대상을 지정. URL/HTTP 메소드별로 관리 가능,
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER 만 접근 가능
                    .anyRequest().authenticated()// 설정 이외 나머지 URL들. authenticated() 를 붙여 인증된 사용자만 접근가능
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        // 로그아웃 기능 설정. 로그아웃 성공시 "/"로 이동
                .and()
                    .oauth2Login() //로그인 설정 시작
                        .userInfoEndpoint() //OAuth2 로그인 성공이후 사용자 정보를 가져올 때 설정 담당
                            .userService(customOAuth2UserService); //소셜 로그인 성공 후 후속조치를 진행할 Service 인터페이스 구현체 등록
                                // 리소스 서버(즉, 소셜서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시
    }

}
