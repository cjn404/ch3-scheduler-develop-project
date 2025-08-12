package org.example.ch3schedulerdevelopproject.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.ch3schedulerdevelopproject.dto.ScheduleDeleteRequest;
import org.example.ch3schedulerdevelopproject.dto.ScheduleRequest;
import org.example.ch3schedulerdevelopproject.dto.ScheduleResponse;
import org.example.ch3schedulerdevelopproject.entity.Schedule;
import org.example.ch3schedulerdevelopproject.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 생성
    @Transactional
    public ScheduleResponse save(ScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getPassword(),
                request.getName(),
                request.getTitle(),
                request.getContent()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(
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
    public List<ScheduleResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleResponse> dtos = new ArrayList<>();

        for (Schedule schedule : schedules) {
            ScheduleResponse scheduleResponse = new ScheduleResponse(
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
    public ScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new EntityNotFoundException("Schedule with id " + scheduleId + " not found")
        );
        return new ScheduleResponse(
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
    public ScheduleResponse updateSchedule(Long scheduleId, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new EntityNotFoundException("Schedule with id " + scheduleId + " not found")
        );
        if (!ObjectUtils.nullSafeEquals(schedule.getPassword(), request.getPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }

        schedule.updateSchedule(request.getName(), request.getTitle(), request.getContent());
        return new ScheduleResponse(
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
    public void deleteSchedule(Long scheduleId, ScheduleDeleteRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new EntityNotFoundException("Schedule with id " + scheduleId + " not found")
        );
        if (!ObjectUtils.nullSafeEquals(schedule.getPassword(), request.getPassword())) {
            throw new IllegalStateException("Passwords do not match");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
