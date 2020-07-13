package com.ojj.book.springboot.web.domain.posts;


import com.ojj.book.springboot.domain.posts.Posts;
import com.ojj.book.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After  // JUnit 단위테스트 끝날때마다 수행되는 메서드 여러 테스트를 진행할때 다른 테스트에 영향주지않기위해.
    public void cleaneup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder().title(title)      //posts 테이블이 Entity 로 자동생성됬으니 여기에 insert/update
                                            .content(content)  //id 값이 있으면 update 없으면  insert 가 실행됨
                                            .author("ojjang")
                                            .build());

        //when
        List<Posts> postsList = postsRepository.findAll();   // 테이블의 모든 데이터 조회

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }


}
