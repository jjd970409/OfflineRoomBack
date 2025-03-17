package com.escape.offlineroom.controller;

import com.escape.offlineroom.dto.LoginRequest;
import com.escape.offlineroom.entity.User;
import com.escape.offlineroom.service.UserService;
import com.escape.offlineroom.util.JwtTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // JwtTokenUtil 주입

    @GetMapping("/test") // <-- 추가
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Proxy Test OK");
    }

    // JWT secret key (클래스 멤버 변수로 선언)
    private Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userService.findByUserId(loginRequest.getUserId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
                // 로그인 성공

                String token = jwtTokenUtil.generateToken(user.getUserId(), user.getId());

                Map<String, String> response = new HashMap<>();
                response.put("accessToken", token);
                response.put("id", user.getId().toString());
                response.put("response_msg", "로그인 성공");

                // TODO:  JWT 토큰 생성 및 반환 또는 세션 기반 인증 처리
                return ResponseEntity.ok(response);
            } else {
                // 비밀번호 불일치
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 틀렸습니다.");
            }
        } else {
            // 사용자 없음
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 틀렸습니다.");
        }
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        try {
            // 1. Authorization 헤더에서 토큰 추출
            String token = jwtTokenUtil.resolveToken(request);

            // 2. 토큰 검증
            if (token != null && jwtTokenUtil.validateToken(token)) {
                // 3. 토큰에서 사용자 ID 추출
                String userId = jwtTokenUtil.getUserIdFromToken(token);

                // 4. 사용자 정보 조회
                Optional<User> userOptional = userService.findByUserId(userId);

                if (userOptional.isPresent()) {
                    User user = userOptional.get();

                    // 5. 응답 (필요한 정보만 포함)
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("id", user.getId()); //또는 username
                    userInfo.put("userName", user.getName()); // 필요하다면
                    return ResponseEntity.ok(userInfo);

                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting user info");
        }
    }
}
