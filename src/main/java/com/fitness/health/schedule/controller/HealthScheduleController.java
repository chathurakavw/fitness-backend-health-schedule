package com.fitness.health.schedule.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.health.schedule.dto.ResponseDto;
import com.fitness.health.schedule.dto.UserDto;
import com.fitness.health.schedule.dto.UserScheduleDto;
import com.fitness.health.schedule.entity.ScheduleType;
import com.fitness.health.schedule.entity.UserSchedule;
import com.fitness.health.schedule.repository.ScheduleTypeRepository;
import com.fitness.health.schedule.repository.UserScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class HealthScheduleController {

    @Autowired
    private ScheduleTypeRepository scheduleTypeRepository;

    @Autowired
    private UserScheduleRepository userScheduleRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.auth.user:NA}")
    private String apiAuthUrl;

    @GetMapping(path = "/schedule-type-list")
    public ResponseDto<?> scheduleTypeList() {
        List<ScheduleType> scheduleTypeList = scheduleTypeRepository.findAll();
        if (!scheduleTypeList.isEmpty()) {
            return ResponseDto.builder().status(true).data(scheduleTypeList).build();
        }
        return ResponseDto.builder().status(false).build();
    }


    @PostMapping(path = "/create-schedule")
    public ResponseDto<?> createPayment(@RequestBody UserScheduleDto userScheduleDto) throws JsonProcessingException {

        ResponseEntity<String> response = restTemplate.getForEntity(apiAuthUrl + userScheduleDto.getUserId(), String.class);
        ObjectMapper mapper = new ObjectMapper();
        if (response.getBody() != null) {
            JsonNode root = mapper.readTree(response.getBody());
            UserDto userDto = mapper.treeToValue(root, UserDto.class);

            UserSchedule userSchedule = new UserSchedule();
            userSchedule.setScheduleTypeId(userScheduleDto.getScheduleTypeId());
            userSchedule.setUserId(userDto.getId());
            userSchedule.setDescription(userScheduleDto.getDescription());
            userScheduleRepository.save(userSchedule);
            return ResponseDto.builder().status(true).message("User schedule has been successfully created").build();
        } else {
            return ResponseDto.builder().status(false).message("User not found").build();
        }
    }

    @GetMapping(path = "/user-schedules/{userId}")
    public ResponseDto<?> userSchedule(@PathVariable Integer userId) {
        List<UserSchedule> userSchedules = userScheduleRepository.findAllByUserId(userId);
        if (!userSchedules.isEmpty()) {
            return ResponseDto.builder().status(true).data(userSchedules).build();
        }
        return ResponseDto.builder().status(false).build();
    }
}
