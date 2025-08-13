package org.example.ch3schedulerdevelopproject.dto;

import lombok.Getter;

@Getter
public class AuthResponse {

    private final Long id;
    private final String name;
    private final String email;

    public AuthResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
