package com.escape.offlineroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired; // JWT 인증 필터 추가 시 필요


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // JWT 인증 필터를 사용하는 경우, 필터를 주입받습니다.
    // @Autowired
    // private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(authz -> authz  // 요청 권한 설정
                        .requestMatchers("/api/login", "/api/register").permitAll() // 로그인, 회원가입은 허용
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .formLogin(formLogin -> formLogin.disable())  // Form Login 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()); // HTTP Basic 인증 비활성화

        // JWT 인증 필터를 추가하는 경우 (UsernamePasswordAuthenticationFilter 앞에 추가)
        // http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 세션을 사용하지 않는 경우 (JWT 사용 시)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}