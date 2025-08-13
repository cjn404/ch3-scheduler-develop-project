package org.example.ch3schedulerdevelopproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String name;
    private String email;

    // Lv2
    public User(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Lv4 로그인용 생성자
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void updateUser(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
