package org.example.ch3schedulerdevelopproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequest {

    @NotBlank
    private String password;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
