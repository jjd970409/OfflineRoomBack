package com.escape.offlineroom.repository;

import com.escape.offlineroom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId); // userId로 User를 찾는 메서드
}
