package org.example.ch3schedulerdevelopproject.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.ch3schedulerdevelopproject.dto.ScheduleDeleteRequest;
import org.example.ch3schedulerdevelopproject.dto.ScheduleRequest;
import org.example.ch3schedulerdevelopproject.dto.ScheduleResponse;
import org.example.ch3schedulerdevelopproject.dto.UserResponse;
import org.example.ch3schedulerdevelopproject.entity.Schedule;
import org.example.ch3schedulerdevelopproject.entity.User;
import org.example.ch3schedulerdevelopproject.repository.ScheduleRepository;
import org.example.ch3schedulerdevelopproject.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 헬퍼 메서드 - 반환부 반복되는 코드 처리
    private ScheduleResponse toScheduleResponse(Schedule schedule) {
        return new ScheduleResponse(
                new UserResponse(
                        schedule.getUser().getId(),
                        schedule.getUser().getName(),
                        schedule.getUser().getEmail(),
                        schedule.getUser().getCreatedAt(),
                        schedule.getUser().getModifiedAt()
                ),
                schedule.getId(),
                schedule.getUser().getName(),  // Schedule name 대신 User name 사용
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // 생성
    @Transactional
    public ScheduleResponse save(Long userId, ScheduleRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 ID가 없습니다.")
        );
        // 평문 비밀번호 -> 해시 변환
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Schedule schedule = new Schedule(
                user,
                encodedPassword,
                request.getTitle(),
                request.getContent()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return toScheduleResponse(savedSchedule);
    }

    // 목록 조회
    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAll(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 ID가 없습니다.")
        );

        List<Schedule> schedules = scheduleRepository.findAllByUser(user);
        List<ScheduleResponse> dtos = new ArrayList<>();

        for (Schedule schedule : schedules) {
            dtos.add(toScheduleResponse(schedule));
        }
        return dtos;
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public ScheduleResponse findOne(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByUser_IdAndId(userId, scheduleId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 ID가 없습니다.")
        );
        return toScheduleResponse(schedule);
    }

    // 수정
    @Transactional
    public ScheduleResponse updateSchedule(Long userId, Long scheduleId, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findByUser_IdAndId(userId, scheduleId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 ID가 없습니다.")
        );
        if (!passwordEncoder.matches(request.getPassword(), schedule.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
//        if (!ObjectUtils.nullSafeEquals(schedule.getPassword(), request.getPassword())) {
//            throw new IllegalStateException("Passwords do not match");
//        }

        schedule.updateSchedule(request.getTitle(), request.getContent());
        return toScheduleResponse(schedule);
    }

    // 삭제
    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId, ScheduleDeleteRequest request) {
        Schedule schedule = scheduleRepository.findByUser_IdAndId(userId, scheduleId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 ID가 없습니다.")
        );
        if (!passwordEncoder.matches(request.getPassword(), schedule.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
//        if (!ObjectUtils.nullSafeEquals(schedule.getPassword(), request.getPassword())) {
//            throw new IllegalStateException("Passwords do not match");
//        }
        // 위에서 schedule 객체를 DB에서 조회했으므로, 해당 객체 바로 삭제
        scheduleRepository.delete(schedule);
    }
}
