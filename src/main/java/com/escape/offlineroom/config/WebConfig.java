package com.escape.offlineroom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Spring 설정 클래스임을 나타냄 -> 꼭 어노테이션 추가해야함 !!
public class WebConfig implements WebMvcConfigurer { // WebMvcConfigurer 인터페이스 구현

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 설정 적용
                .allowedOrigins("http://localhost:8081") // Vue.js 개발 서버 (8081) 허용
                //.allowedOrigins("http://example.com") // 운영 환경에서는 실제 Vue.js 앱 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*")    // 모든 헤더 허용
                .allowCredentials(true)  // 쿠키 허용 (세션 기반 인증 시 필요)
                .maxAge(3600);         // preflight 요청 캐싱 시간 (초)
    }
}
