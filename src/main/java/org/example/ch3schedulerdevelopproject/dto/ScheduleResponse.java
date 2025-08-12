package org.example.ch3schedulerdevelopproject.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponse {

    private final Long userId;
    private final String userName;
    private final String userEmail;
    private final Long scheduleId;
    private final String scheduleName;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ScheduleResponse(
            Long userId,
            String userName,
            String userEmail,
            Long scheduleId,
            String scheduleName,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.scheduleId = scheduleId;
        this.scheduleName = scheduleName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
