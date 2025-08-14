package org.example.ch3schedulerdevelopproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ch3schedulerdevelopproject.dto.ScheduleDeleteRequest;
import org.example.ch3schedulerdevelopproject.dto.ScheduleRequest;
import org.example.ch3schedulerdevelopproject.dto.ScheduleResponse;
import org.example.ch3schedulerdevelopproject.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 생성
    @PostMapping("/users/{userId}/schedules")
    public ResponseEntity<ScheduleResponse> saveSchedule(
            @PathVariable Long userId,
            @Valid @RequestBody ScheduleRequest request
    ) {
        return ResponseEntity.ok(scheduleService.save(userId, request));
    }

    // 목록 조회
    @GetMapping("/users/{userId}/schedules")
    public ResponseEntity<List<ScheduleResponse>> findAllSchedules(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(scheduleService.findAll(userId));
    }

    // 단건 조회
    @GetMapping("/users/{userId}/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> findOneSchedule(
            @PathVariable Long userId,
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(scheduleService.findOne(userId, scheduleId));
    }

    // 수정
    @PatchMapping("/users/{userId}/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable Long userId,
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleRequest request
    ) {
        return ResponseEntity.ok(scheduleService.updateSchedule(userId, scheduleId, request));
    }

    // 삭제
    @DeleteMapping("/users/{userId}/schedules/{scheduleId}")
    public void deleteSchedule(
            @PathVariable Long userId,
            @PathVariable Long scheduleId,
            @RequestBody ScheduleDeleteRequest request
    ) {
        scheduleService.deleteSchedule(userId, scheduleId, request);
    }

}
