package org.example.ch3schedulerdevelopproject.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
// 정적 팩토리 메서드 또는 Mapper로 아래 로직 분리시켜보기
// -> Service에서 DTO 변환 로직 간소화될 수 있음
public class ScheduleResponse {

    private final UserResponse userResponse;
    private final Long scheduleId;
    private final String scheduleAuthorName;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public ScheduleResponse(
            UserResponse userResponse,
            Long scheduleId,
            String scheduleAuthorName,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.userResponse = userResponse;
        this.scheduleId = scheduleId;
        this.scheduleAuthorName = scheduleAuthorName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
