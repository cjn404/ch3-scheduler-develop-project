package org.example.ch3schedulerdevelopproject.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.ch3schedulerdevelopproject.auth.dto.AuthRequest;
import org.example.ch3schedulerdevelopproject.auth.dto.AuthResponse;
import org.example.ch3schedulerdevelopproject.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody AuthRequest request
    ) {
        AuthResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request,
            HttpServletRequest servletRequest
    ) {
        AuthResponse result = authService.login(request);

        HttpSession session = servletRequest.getSession();  // 신규 세션 생성
        session.setAttribute("LOGIN_USER", result.getId()); // "sessionKey값"
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest servletRequest) {
        // 로그인하지 않으면 HttpSession -> Null로 반환
        HttpSession session = servletRequest.getSession(false);
        // 세션 존재 시 = 로그인이 된 경우
        if (session != null) {
            session.invalidate();   //  해당 세션(데이터) 삭제
        }
    }
}
