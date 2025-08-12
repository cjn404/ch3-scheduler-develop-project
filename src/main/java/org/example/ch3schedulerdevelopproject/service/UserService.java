package org.example.ch3schedulerdevelopproject.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.ch3schedulerdevelopproject.dto.UserDeleteRequest;
import org.example.ch3schedulerdevelopproject.dto.UserRequest;
import org.example.ch3schedulerdevelopproject.dto.UserResponse;
import org.example.ch3schedulerdevelopproject.entity.User;
import org.example.ch3schedulerdevelopproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 생성
    @Transactional
    public UserResponse save(UserRequest request) {
        User user = new User(
                request.getPassword(),
                request.getName(),
                request.getEmail());

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt());
    }

    // 목록 조회
    // 특정 유저(ID)가 아니므로 findAll() 파라미터 없음
    @Transactional(readOnly = true)
    // 목록 조회이므로 메서드 리턴 타입이 리스트
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            );
            dtos.add(userResponse);
        }
        return dtos;
    }

    // 단건 조회
    // 특정 유저(ID)이므로 findOne() 파라미터가 기준이 되는 특정 유저(ID)
    @Transactional(readOnly = true)
    public UserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        );
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // 단건 수정
    // 생성 때는 생성이라서 ID 없지만, 수정은 생성된 ID로 특정 가능 -> 파라미터로 받음
    @Transactional
    public UserResponse update(Long userId, UserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        );
        // NSF하게 비밀번호 검증
        if (!ObjectUtils.nullSafeEquals(user.getPassword(), user.getPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // 단건 삭제
    // 삭제 시 경로 변수(@PathVariable)로 ID만 전달하면 되므로, 매개변수로 DTO(REQUEST) 받지 않음
    // 단, 비밀번호 검증 시 비밀번호를 추가로 받아야 함
    // @RequestParam으로 받을 경우 매개변수로 비밀번호를 직접 받지만,
    // 보안상 @RequestBody로 받을 예정이므로
    // -> 비밀번호만 담는 PasswordRequest 또는 DeleteRequest DTO 별도 생성
    @Transactional
    public void delete(Long userId, UserDeleteRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        );
        // NSF하게 비밀번호 검증
        if (!ObjectUtils.nullSafeEquals(user.getPassword(), user.getPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }
        userRepository.delete(user);
    }
}
