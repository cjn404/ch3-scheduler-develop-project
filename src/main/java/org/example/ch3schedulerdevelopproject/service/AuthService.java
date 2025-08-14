package org.example.ch3schedulerdevelopproject.service;

import lombok.RequiredArgsConstructor;
import org.example.ch3schedulerdevelopproject.dto.AuthRequest;
import org.example.ch3schedulerdevelopproject.dto.AuthResponse;
import org.example.ch3schedulerdevelopproject.entity.User;
import org.example.ch3schedulerdevelopproject.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse signup(AuthRequest request) {
        // 이메일 중복 여부 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다.");
        }

        // 비밀 번호 암호화(평문 -> 해시값)
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 유저 생성
        User user = new User(encodedPassword, request.getName(), request.getEmail());
        userRepository.save(user);
        return new AuthResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 ID가 없습니다.")
        );
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return new AuthResponse(user.getId(), user.getName(), user.getEmail());
    }
}
