package com.practice.javagroupiiminiproject.model.response;

import com.practice.javagroupiiminiproject.model.entity.HabitStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HabitLogResponse {
    private Long habitLogId;
    private LocalDate logDate;
    private HabitStatus status;
    private int xpEarned;
    private HabitResponse habit;
}
