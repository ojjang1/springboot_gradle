package com.ojj.book.springboot.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * JPA Auditing 로 생성시간/수정시간 자동화하기위한 엔티티
 * 모든 Entity 의 상위 클래스가 되어 Entity들의 CreatedDate, modifiedDate를 자동으로 관리하는 역활
 */
@Getter
@MappedSuperclass   //JPA Entity 들이 이 클래스를 상속할 경우 필드들(createdDate,modifiedDate) 도 컬럼으로 인식하게해줌
@EntityListeners(AuditingEntityListener.class)  //Auditing 기증을 포함시킴
public class BaseTimeEntity {

    @CreatedDate    //Entity가 생성되어 저장될때 시간이 자동저장
    private LocalDateTime createdDate;

    @LastModifiedDate   //조회한 Entity의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;
}
