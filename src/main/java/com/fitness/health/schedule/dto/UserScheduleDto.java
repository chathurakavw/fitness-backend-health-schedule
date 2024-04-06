package com.fitness.health.schedule.dto;

import lombok.Data;

@Data
public class UserScheduleDto {
    private Integer scheduleTypeId;
    private String description;
    private Integer userId;
}
