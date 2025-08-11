package org.example.ch3schedulerdevelopproject.repository;

import org.example.ch3schedulerdevelopproject.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
