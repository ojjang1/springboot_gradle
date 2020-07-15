package com.ojj.book.springboot.web;

import com.ojj.book.springboot.config.auth.LoginUser;
import com.ojj.book.springboot.config.auth.dto.SessionUser;
import com.ojj.book.springboot.service.posts.PostsService;
import com.ojj.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;  // service 영역을 통해 CRUD 사용
//    private final HttpSession httpSession;  //@LoginUser 어노테이션 생성 후 필요없으짐

    @GetMapping("/")   // 기본 url ,  template 폴더에 index.mustache 를 매핑
    public String index(Model model , @LoginUser SessionUser user) {  // Model 은 서버템플릿 엔진에서 사용할 수 있는 객체를 저장
        model.addAttribute("posts", postsService.findAllDesc()); // 'posts' 라는 이름으로 객체를 화면에 전달

        // CustomOAuth2UserService 에서 로그인 성공 시 세션에 SessionUser를 저장 했으므로
        // 여기선 getAttribute 로 저장된 정보를 꺼낼 수 있다.
        // 삭제된 이유 => 어노테이션 생성으로 파라미터로 세션에서 바로 받아올 수 있기 때문에
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        String name;

        if (user != null) {
//            System.out.println(user.getName()); //user이름 잘 들어오는지 테스트
            model.addAttribute("userName", user.getName());
            name = user.getName();
            model.addAttribute("name",name);
            // 둘다 의미는 같은데 왜 위에것은 "admin", 아래것은 "정확한이름" 이렇게 바뀔까.
            // index.mustache 에서 userName 으로 받는데 windows 에서 해당 변수로
            // 시스템 로그인의 userName 변수로 저장이 되어서 윈도우 사용자 이름이 화면에 뜸
            // 그러므로 화면단에 UserName 변수를 사용하지 않는것이 좋다.
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
