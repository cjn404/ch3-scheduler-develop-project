package org.example.ch3schedulerdevelopproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthRequest {

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
