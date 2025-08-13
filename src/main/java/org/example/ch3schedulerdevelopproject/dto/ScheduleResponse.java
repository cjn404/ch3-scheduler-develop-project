package org.example.ch3schedulerdevelopproject.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
// 정적 팩토리 메서드 또는 Mapper로 아래 로직 분리시켜보기
// -> Service에서 DTO 변환 로직 간소화될 수 있음
public class ScheduleResponse {

    private final UserResponse userResponse;
//    private final Long userId;
//    private final String userName;
//    private final String userEmail;
    private final Long scheduleId;
    private final String scheduleName;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public ScheduleResponse(
            UserResponse userResponse,
//            Long userId,
//            String userName,
//            String userEmail,
            Long scheduleId,
            String scheduleName,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.userResponse = userResponse;
//        this.userId = userId;
//        this.userName = userName;
//        this.userEmail = userEmail;
        this.scheduleId = scheduleId;
        this.scheduleName = scheduleName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
