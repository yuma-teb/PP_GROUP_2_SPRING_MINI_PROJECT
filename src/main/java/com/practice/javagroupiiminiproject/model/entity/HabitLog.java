package com.practice.javagroupiiminiproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitLog {
    private UUID habitLogId;
    private LocalDate logDate;
    private HabitStatus status;
    private int xpEarned;
    private UUID habitId;
}
