package com.ojj.book.springboot.web;

import com.ojj.book.springboot.service.posts.PostsService;
import com.ojj.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.PostRemove;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;  // service 영역을 통해 CRUD 사용

    @GetMapping("/")   // 기본 url ,  template 폴더에 index.mustache 를 매핑
    public String index(Model model) {  // 서버템플릿 엔진에서 사용할 수 있는 객체를 저장
        model.addAttribute("posts", postsService.findAllDesc()); // 'posts' 라는 이름으로 객체를 화면에 전달
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
