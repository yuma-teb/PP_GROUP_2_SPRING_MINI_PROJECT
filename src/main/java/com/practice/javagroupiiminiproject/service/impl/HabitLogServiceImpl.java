package com.practice.javagroupiiminiproject.service.impl;

import com.practice.javagroupiiminiproject.model.entity.HabitLog;
import com.practice.javagroupiiminiproject.repository.HabitLogRepository;
import com.practice.javagroupiiminiproject.service.HabitLogService;
import org.springframework.stereotype.Service;

@Service
public class HabitLogServiceImpl implements HabitLogService {
    private final HabitLogRepository habitLogRepository;

    public HabitLogServiceImpl(HabitLogRepository habitLogRepository) {
        this.habitLogRepository = habitLogRepository;
    }

    @Override
    public HabitLog createHabitLog(HabitLog request) {
        return habitLogRepository.saveHabitLog(request);
    }

}
