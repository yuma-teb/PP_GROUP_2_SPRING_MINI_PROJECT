package com.practice.javagroupiiminiproject.controller;

import com.practice.javagroupiiminiproject.model.entity.Habit;
import com.practice.javagroupiiminiproject.model.entity.HabitLog;
import com.practice.javagroupiiminiproject.model.entity.HabitStatus;
import com.practice.javagroupiiminiproject.model.request.CreateHabitLogRequest;
import com.practice.javagroupiiminiproject.model.response.APIResponse;
import com.practice.javagroupiiminiproject.model.response.HabitResponse;
import com.practice.javagroupiiminiproject.service.HabitLogService;
import com.practice.javagroupiiminiproject.service.impl.HabitLogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/habit-logs")
public class HabitLogController {
    private final HabitLogService habitLogService;

    public HabitLogController(HabitLogServiceImpl habitLogService) {
        this.habitLogService = habitLogService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<?>> saveHabitLog(@RequestBody CreateHabitLogRequest request) {
        // todo
        // find habit log by id

        HabitLog habitLog = HabitLog.builder()
                .status(request.getStatus())
                .logDate(LocalDate.now())
                .habitId(request.getHabitId())
                .xpEarned(0)
                .build();

        HabitLog responseHabitLog = habitLogService.createHabitLog(habitLog);

        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.builder()
                        .message("saved successfully!")
                        .status(HttpStatus.CREATED)
                        .payload(responseHabitLog)
                .build());
    }
}
