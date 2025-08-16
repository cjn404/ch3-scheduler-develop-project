package org.example.ch3schedulerdevelopproject.repository;

import org.example.ch3schedulerdevelopproject.entity.Schedule;
import org.example.ch3schedulerdevelopproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUser(User user);

    Optional<Schedule> findByUser_IdAndId(Long userId, Long scheduleId);

    @Transactional
    void deleteByUser_Id(Long userId);
}
