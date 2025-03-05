package com.escape.offlineroom.service;

import com.escape.offlineroom.entity.User;
import com.escape.offlineroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Spring Security에서 제공하는 PasswordEncoder

    public User registerNewUser(User user) { //회원 가입 로직 (필요하다면)
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }


    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
