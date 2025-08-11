package org.example.ch3schedulerdevelopproject.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequest {

    private String password;
    private String name;
    private String title;
    private String content;
}
