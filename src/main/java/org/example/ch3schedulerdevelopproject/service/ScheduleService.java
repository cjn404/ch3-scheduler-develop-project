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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 생성
    @Transactional
    public ScheduleResponse save(Long userId, ScheduleRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId)
        );
        Schedule schedule = new Schedule(
                user,
                request.getPassword(),
                request.getName(),
                request.getTitle(),
                request.getContent()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(
                new UserResponse( // 여기서 UserResponse 객체를 생성
                        savedSchedule.getUser().getId(),
                        savedSchedule.getUser().getName(),
                        savedSchedule.getUser().getEmail(),
                        savedSchedule.getUser().getCreatedAt(),
                        savedSchedule.getUser().getModifiedAt()
                ),
//                savedSchedule.getUser().getId(),
//                savedSchedule.getUser().getName(),
//                savedSchedule.getUser().getEmail(),
                savedSchedule.getId(),
                savedSchedule.getName(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    // 목록 조회
    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAll(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId)
        );

        List<Schedule> schedules = scheduleRepository.findAllByUser(user);
        List<ScheduleResponse> dtos = new ArrayList<>();

        for (Schedule schedule : schedules) {
            ScheduleResponse scheduleResponse = new ScheduleResponse(
                    new UserResponse(
                            schedule.getUser().getId(),
                            schedule.getUser().getName(),
                            schedule.getUser().getEmail(),
                            schedule.getUser().getCreatedAt(),
                            schedule.getUser().getModifiedAt()
                    ),
//                    schedule.getUser().getId(),
//                    schedule.getUser().getName(),
//                    schedule.getUser().getEmail(),
                    schedule.getId(),
                    schedule.getName(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            dtos.add(scheduleResponse);
        }
        return dtos;
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public ScheduleResponse findOne(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByUser_IdAndId(userId, scheduleId).orElseThrow(
                () -> new EntityNotFoundException("Schedule with id " + scheduleId + " not found")
        );
        return new ScheduleResponse(
                new UserResponse(
                        schedule.getUser().getId(),
                        schedule.getUser().getName(),
                        schedule.getUser().getEmail(),
                        schedule.getUser().getCreatedAt(),
                        schedule.getUser().getModifiedAt()
                ),
//                schedule.getUser().getId(),
//                schedule.getUser().getName(),
//                schedule.getUser().getEmail(),
                schedule.getId(),
                schedule.getName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // 수정
    @Transactional
    public ScheduleResponse updateSchedule(Long userId, Long scheduleId, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findByUser_IdAndId(userId, scheduleId).orElseThrow(
                () -> new EntityNotFoundException("Schedule with id " + scheduleId + " not found")
        );
        if (!ObjectUtils.nullSafeEquals(schedule.getPassword(), request.getPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }

        schedule.updateSchedule(request.getName(), request.getTitle(), request.getContent());
        return new ScheduleResponse(
                new UserResponse(
                        schedule.getUser().getId(),
                        schedule.getUser().getName(),
                        schedule.getUser().getEmail(),
                        schedule.getUser().getCreatedAt(),
                        schedule.getUser().getModifiedAt()
                ),
//                schedule.getUser().getId(),
//                schedule.getUser().getName(),
//                schedule.getUser().getEmail(),
                schedule.getId(),
                schedule.getName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }


    // 삭제
    @Transactional
    public void deleteSchedule(Long userId, Long scheduleId, ScheduleDeleteRequest request) {
        Schedule schedule = scheduleRepository.findByUser_IdAndId(userId, scheduleId).orElseThrow(
                () -> new EntityNotFoundException("Schedule with id " + scheduleId + " not found")
        );
        if (!ObjectUtils.nullSafeEquals(schedule.getPassword(), request.getPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
