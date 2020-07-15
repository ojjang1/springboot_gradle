package com.ojj.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User 의 CRUD 담당하는 인터페이스
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);   // email을 통해 이미 생성된 사용자인지 확인
}
