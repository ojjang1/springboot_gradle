package com.ojj.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Post 클래스로 Data베이스 접근하게 해주는 인터페이스
// JPA 에서는 repository 라고 부르며, dao 역활? mapper인터페이스 같은건가?
// @Repository 설정 안해주고 단순히 JpaRepository 상속받는것으로 기본 CRUD 쿼리들을 만들어준다.
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")  //기본 제공하는 쿼리 이외에 등록하기
    List<Posts> findAllDesc();


}
