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
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Schedule(User user, String password, String title, String content) {
        this.user = user;
        this.password = password;
        this.title = title;
        this.content = content;
    }

    // 일정만 수정하므로 User user 파라미터를 추가 X
    public void updateSchedule(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
