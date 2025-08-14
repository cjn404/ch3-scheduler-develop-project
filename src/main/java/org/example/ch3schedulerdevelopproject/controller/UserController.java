package org.example.ch3schedulerdevelopproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ch3schedulerdevelopproject.dto.UserDeleteRequest;
import org.example.ch3schedulerdevelopproject.dto.UserRequest;
import org.example.ch3schedulerdevelopproject.dto.UserResponse;
import org.example.ch3schedulerdevelopproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** 생성 API
     * URL(/users)에 POST 요청을 보내면 이 메서드가 실행
     *    - 요청 body: UserRequest (클라이언트가 보낸 JSON → DTO 변환)
     *    - 응답 body: UserResponse
     *
     * - 흐름:
     *     1. userService.save(request) 호출하여 사용자 생성 비즈니스 로직 수행
     *     2. 결과를 DTO(UserResponse)로 반환
     *     3. ResponseEntity.ok(): HTTP 상태 코드 200 OK 응답 및 반환 객체를 ResponseEntity가 감싸서 전달
     *
     * @param request 생성할 사용자 정보
     * @return HTTP 200 OK와 함께 생성된 사용자 정보
     */
//    @PostMapping("/users")
//    public ResponseEntity<UserResponse> saveUser(
//            @RequestBody UserRequest request) {
//        return ResponseEntity.ok(userService.save(request));
//    }

    /** 목록 조회
     * URL(/users)에 GET 요청을 보내면 이 메서드가 실행
     * 전체 조회로 별도의 매개변수 필요없음
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /** 단건 조회
     * URL(/users/{userId})에 GET 요청을 보내면 이 메서드가 실행
     * -> @PathVariable Long userId 필요
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> findOneUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.findOne(userId));
    }

    /** 단건 수정
     * URL(/users/{userId})에 PATCH 요청을 보내면 이 메서드가 실행
     * -> @PathVariable Long userId 필요
     * 수정 내용을 body에 받음
     * -> @RequestBody UserRequest request 필요
     */
    @PatchMapping("/users/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userService.update(userId, request));
    }

    /**
     * 단건 삭제
     * URL(/users/{userId})에 DELETE 요청을 보내면 이 메서드가 실행
     * -> @PathVariable Long userId 필요
     * 비밀번호를 body로 DTO 객체 요청
     * -> @RequestBody UserDeleteRequest request 필요
     */
    @DeleteMapping("/users/{userId}")
    public void deleteUser(
            @PathVariable Long userId,
            @RequestBody UserDeleteRequest request
    ) {
        userService.delete(userId, request);
    }
}
