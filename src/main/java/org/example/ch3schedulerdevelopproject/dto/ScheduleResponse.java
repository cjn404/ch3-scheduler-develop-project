package org.example.ch3schedulerdevelopproject.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponse {

    private final Long id;
    private final String name;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ScheduleResponse(
            Long id,
            String name,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
