package com.ojj.book.springboot.service.posts;


import com.ojj.book.springboot.domain.posts.Posts;
import com.ojj.book.springboot.domain.posts.PostsRepository;

import com.ojj.book.springboot.web.dto.PostsListResponseDto;
import com.ojj.book.springboot.web.dto.PostsResponseDto;
import com.ojj.book.springboot.web.dto.PostsSaveRequestDto;
import com.ojj.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
        );

        // 더티 체킹!(왜 업데이트 쿼리가 없을까?)
        // JPA 의 영속성 컨텍스트(엔티티를 영구 저장하는 환경) 때문에
        // PostRepository 를 하용해 쿼리를 날리지 않아도
        // 이미 트렌젝션 안에서 findById로 해당 엔티티를 불러왔으면 영속성이 적용되어
        // 엔티티를 수정만 해도 트렌젝션이 끝나는 순간 데이터베이스의 내용도 변경된다.
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }


    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
        );

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)  //트랜젝션 범위는 유지 하되 조회 기능만 남겨두어 조회 속도가 개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
        );

        postsRepository.delete(posts); //엔티티 자체로 삭제 가능 혹은 id를 넣어서도 삭제 가능
    }

}
