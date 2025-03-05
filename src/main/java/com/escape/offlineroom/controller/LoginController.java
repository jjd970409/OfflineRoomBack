package com.escape.offlineroom.controller;

import com.escape.offlineroom.dto.LoginRequest;
import com.escape.offlineroom.entity.User;
import com.escape.offlineroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/test") // <-- 추가
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Proxy Test OK");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userService.findByUserId(loginRequest.getUserId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
                // 로그인 성공
                // TODO:  JWT 토큰 생성 및 반환 또는 세션 기반 인증 처리
                return ResponseEntity.ok("로그인 성공"); // 간단한 성공 메시지
            } else {
                // 비밀번호 불일치
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 틀렸습니다.");
            }
        } else {
            // 사용자 없음
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 틀렸습니다.");
        }
    }
}
