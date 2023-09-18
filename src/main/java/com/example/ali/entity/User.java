package com.example.ali.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("checkstyle:Indentation")
@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String email;
    private Double point;
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        if (this.role == UserRoleEnum.USER) {
            this.point = 10000D;
        } else {
            this.point = 0D;
        }
    }

    public void changePoint(Double price) {
        this.point -= price;
    }


    // getters and setters
}
