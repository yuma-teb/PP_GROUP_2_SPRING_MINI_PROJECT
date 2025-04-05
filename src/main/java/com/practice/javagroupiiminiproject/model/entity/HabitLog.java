package com.practice.javagroupiiminiproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitLog {
    private Long habitLogId;
    private LocalDate logDate;
    private HabitStatus status; // use the enum
    private int xpEarned;
    private Long habitId; // FK to habits
}
