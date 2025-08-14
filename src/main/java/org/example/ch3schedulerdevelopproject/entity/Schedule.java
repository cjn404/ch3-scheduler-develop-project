package org.example.ch3schedulerdevelopproject.entity;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Schedule(User user, String password, String title, String content) {
        this.user = user;
        this.password = password;
        this.name = user.getName(); // Schedule 생성 및 수정 시 User name 동기화
        this.title = title;
        this.content = content;
    }

    // 일정만 수정하므로 User user 파라미터를 추가 X
    public void updateSchedule(String title, String content) {
        this.name = user.getName();
        this.title = title;
        this.content = content;
    }


}
