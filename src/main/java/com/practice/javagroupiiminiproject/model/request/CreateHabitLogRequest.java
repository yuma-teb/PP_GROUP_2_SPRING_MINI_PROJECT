package com.practice.javagroupiiminiproject.model.request;

import com.practice.javagroupiiminiproject.model.entity.HabitStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateHabitLogRequest {
    private HabitStatus status;
    private UUID habitId;
}
