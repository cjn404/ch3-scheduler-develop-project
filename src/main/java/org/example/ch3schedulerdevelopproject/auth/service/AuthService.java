package org.example.ch3schedulerdevelopproject.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.ch3schedulerdevelopproject.auth.dto.AuthRequest;
import org.example.ch3schedulerdevelopproject.auth.dto.AuthResponse;
import org.example.ch3schedulerdevelopproject.entity.User;
import org.example.ch3schedulerdevelopproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public AuthResponse signup(AuthRequest request) {
        User user = new User(request.getEmail(), request.getPassword());
        userRepository.save(user);
        return new AuthResponse(user.getId());
    }

    @Transactional
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("Invalid email address provided")
        );
        return new AuthResponse(user.getId());
    }
}
