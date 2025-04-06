package com.practice.javagroupiiminiproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Habit {
    private UUID habitId;
    private String title;
    private String description;
    private HabitFrequency frequency;
    private Boolean isActive;
    private AppUser appUser;
    private LocalDate createdAt;
}
