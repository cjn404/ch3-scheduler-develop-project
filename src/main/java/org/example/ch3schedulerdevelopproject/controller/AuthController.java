package org.example.ch3schedulerdevelopproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ch3schedulerdevelopproject.dto.AuthRequest;
import org.example.ch3schedulerdevelopproject.dto.AuthResponse;
import org.example.ch3schedulerdevelopproject.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원 가입
    @PostMapping("/users/signup")
    public ResponseEntity<AuthResponse> signup(
            @Valid @RequestBody AuthRequest request
    ) {
        AuthResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request,
            HttpServletRequest servletRequest
    ) {
        AuthResponse result = authService.login(request);

        HttpSession session = servletRequest.getSession();  // 신규 세션 생성
        session.setAttribute("LOGIN_USER", result.getId()); // "sessionKey값"

        // 세션 만료 시간(30분) 설정
        session.setMaxInactiveInterval(30 * 60);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/users/logout")
    public void logout(HttpServletRequest servletRequest) {
        // 로그인하지 않으면 HttpSession -> Null로 반환
        HttpSession session = servletRequest.getSession(false);
        // 세션 존재 시 = 로그인이 된 경우
        if (session != null) {
            session.invalidate();   //  해당 세션(데이터) 삭제
        }
    }
}
