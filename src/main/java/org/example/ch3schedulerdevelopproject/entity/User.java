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
