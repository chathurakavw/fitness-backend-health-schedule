package com.fitness.health.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "schedule_type_id")
    private Integer scheduleTypeId;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "schedule_type_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private ScheduleType scheduleTypeByscheduleTypeId;
}
