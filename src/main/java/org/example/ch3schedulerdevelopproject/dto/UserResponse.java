package org.example.ch3schedulerdevelopproject.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private final Long userId;
    private final String userName;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserResponse(Long userId, String userName, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
