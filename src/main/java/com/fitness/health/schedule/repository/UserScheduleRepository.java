package com.fitness.health.schedule.repository;

import com.fitness.health.schedule.entity.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Integer> {
    List<UserSchedule> findAllByUserId(Integer id);
}
