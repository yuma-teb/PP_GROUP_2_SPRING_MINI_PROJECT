package com.practice.javagroupiiminiproject.service;
import com.practice.javagroupiiminiproject.model.entity.HabitLog;

import java.util.List;
import java.util.UUID;

public interface HabitLogService {
    HabitLog createHabitLog(HabitLog request);

    List<HabitLog> getHabitLogById(UUID habitId, Integer page, Integer size);
}
