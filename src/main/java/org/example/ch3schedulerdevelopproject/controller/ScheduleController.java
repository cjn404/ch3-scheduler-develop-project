package org.example.ch3schedulerdevelopproject.controller;

import lombok.RequiredArgsConstructor;
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
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponse> saveSchedule(
            @RequestBody ScheduleRequest request
    ) {
        return ResponseEntity.ok(scheduleService.save(request));
    }

    // 목록 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponse>> findAllSchedules(
    ) {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    // 단건 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> findOneSchedule(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(scheduleService.findOne(scheduleId));
    }

    // 수정
    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequest request
    ) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, request));
    }

    // 삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public void deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestBody String schedulePassword
    ) {
        scheduleService.deleteSchedule(scheduleId, schedulePassword);
    }

}
