package com.practice.javagroupiiminiproject.model.response;

import com.practice.javagroupiiminiproject.model.entity.AppUser;
import com.practice.javagroupiiminiproject.model.entity.HabitFrequency;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class HabitResponse {
    private UUID habitId;
    private String title;
    private String description;
    private HabitFrequency frequency;
    private boolean isActive;
    private AppUser appUserResponse;
    private LocalDate createdAt;
}
