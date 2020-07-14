package com.ojj.book.springboot.domain.posts;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 필드의 getter 메서드 자동생성
@NoArgsConstructor //기본생성자 자동 추가
@Entity   //JPA로 테이블과 링크된 클래스임을 알려줌 (기본값으로 클래스이름으로 테이블 자동생성)
public class Posts {

    @Id // 테이블의 PK 역활
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //PK 생성규칙 (auto_increment 를 붙여줌.)
    private Long id;

    @Column(length = 500, nullable = false)  // 플드명으로 선언안해도 된다. 옵션 넣고싶을떄 넣어줌.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder  // 빌더패턴 클래스 생성? 생성자에 포함된 빌드만 빌더에 추가 (setter를 만들지 않고, 메서드화 시켜 입력 목적을 명확하게
    public Posts(String title, String content, String author) {
        // 값을 입력할때 생성자를 통해서 값을 채우고 db에 넣는 방식, 값변경이 필요할때는 메소드를 호출하여 변경하는방법
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
