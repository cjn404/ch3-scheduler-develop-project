package org.example.ch3schedulerdevelopproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String name;
    private String title;
    private String content;

    public Schedule(String password, String name, String title, String content) {
        this.password = password;
        this.name = name;
        this.title = title;
        this.content = content;
    }

    public void updateSchedule(String name, String title, String content) {
        this.name = name;
        this.title = title;
        this.content = content;
    }


}
