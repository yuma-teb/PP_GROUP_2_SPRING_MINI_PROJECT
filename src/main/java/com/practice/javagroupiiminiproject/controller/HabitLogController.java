package com.practice.javagroupiiminiproject.controller;

import com.practice.javagroupiiminiproject.model.entity.HabitLog;
import com.practice.javagroupiiminiproject.model.request.CreateHabitLogRequest;
import com.practice.javagroupiiminiproject.model.response.APIResponse;
import com.practice.javagroupiiminiproject.service.HabitLogService;
import com.practice.javagroupiiminiproject.service.impl.HabitLogServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/habit-logs")
@Validated
public class HabitLogController {
    private final HabitLogService habitLogService;

    public HabitLogController(HabitLogServiceImpl habitLogService) {
        this.habitLogService = habitLogService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<?>> saveHabitLog(@RequestBody CreateHabitLogRequest request) {
        // todo
        // find habit log by id

        HabitLog habitLog = HabitLog.builder().status(request.getStatus()).logDate(LocalDate.now()).habitId(request.getHabitId()).xpEarned(0).build();

        HabitLog responseHabitLog = habitLogService.createHabitLog(habitLog);

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.builder().message("saved successfully!").status(HttpStatus.CREATED).payload(responseHabitLog).build());
    }

    @GetMapping("/{habit-id}")
    public ResponseEntity<APIResponse<?>> getHabitLogById(
            @PathVariable("habit-id") UUID habitId,
            @RequestParam(defaultValue = "0") @Min(value = 1, message = "Page index must not be less than zero") Integer page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must not be less than one")
            @Max(value = 100, message = "Page size must not be greater than 100") Integer size) {

        List<HabitLog> habitLogs = habitLogService.getHabitLogById(habitId, page, size);

        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Habit logs retrieved successfully")
                        .payload(habitLogs)
                        .build()
        );
    }
}
