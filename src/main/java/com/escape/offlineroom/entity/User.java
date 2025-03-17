package com.escape.offlineroom.entity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(name = "member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) //null 불가
    private String userId;

    @Column(nullable = false) //null 불가
    private String password;

    private String name;
    private String email;

    public User() {}

    // 모든 필드를 포함하는 생성자 (선택 사항)
    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
