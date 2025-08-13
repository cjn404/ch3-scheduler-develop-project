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

    @Column(nullable = false)   // 반드시 값이 있어야 함
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)    // 유니크 키 설정
    private String email;

    public User(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void updateUser(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
