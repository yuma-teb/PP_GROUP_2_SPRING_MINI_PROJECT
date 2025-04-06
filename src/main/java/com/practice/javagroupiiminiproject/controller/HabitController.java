package com.practice.javagroupiiminiproject.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.javagroupiiminiproject.model.entity.Habit;
import com.practice.javagroupiiminiproject.model.request.HabitRequest;
import com.practice.javagroupiiminiproject.model.response.APIResponse;
import com.practice.javagroupiiminiproject.service.HabitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/habits")
@RequiredArgsConstructor  
public class HabitController {
  private final HabitService habitService;


  @GetMapping
  public ResponseEntity<?> getAllHabits(@RequestParam(defaultValue = "1") Integer offset, @RequestParam(defaultValue = "10") Integer size) {
    List<?> habits = habitService.getAllHabits(offset, size);
    return ResponseEntity.ok(
      APIResponse.builder()
        .success(true)
        .message("Habits retrieved successfully")
        .status(HttpStatus.OK)
        .payload(habits)
        .timestamps(LocalDateTime.now())
        .build()
    );
  }

  @PostMapping
  public ResponseEntity<?> createHabit(@RequestBody HabitRequest habitRequest) {
    Habit habit = habitService.createHabit(habitRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(
      APIResponse.builder()
        .success(true)
        .message("Habit created successfully")
        .status(HttpStatus.CREATED)
        .payload(habit)
        .timestamps(LocalDateTime.now())
        .build()
    );
  }

  @GetMapping("/{habit-id}")
  public ResponseEntity<?> getHabitById(@PathVariable("habit-id") Long habitId) {
    Habit habit = habitService.getHabitById(habitId);
    return ResponseEntity.ok(
      APIResponse.builder()
        .success(true)
        .message("Habit id "+ habitId + "get successfully")
        .status(HttpStatus.OK)
        .payload(habit)
        .timestamps(LocalDateTime.now())
        .build()
    );
  }


  @PutMapping("/{habit-id}")
  public ResponseEntity<?> updateHabit(@PathVariable("habit-id") Long habitId,@RequestBody HabitRequest habitRequest) {
    Habit habit = habitService.updateHabit(habitId, habitRequest);
    return ResponseEntity.ok(
      APIResponse.builder()
        .success(true)
        .message("Habit id "+ habitId + " updated successfully")
        .status(HttpStatus.OK)
        .payload(habit)
        .timestamps(LocalDateTime.now())
        .build()
    );
  }

  @DeleteMapping("/{habit-id}")
  public ResponseEntity<?> deleteHabit(@PathVariable("habit-id") Long habitId) {
    Habit habit = habitService.deleteHabit(habitId);
    return ResponseEntity.ok(
      APIResponse.builder()
        .success(true)
        .message("Habit id "+ habitId + " deleted successfully")
        .status(HttpStatus.OK)
        .payload(habit)
        .timestamps(LocalDateTime.now())
        .build()
    );
  }
}
