package com.practice.javagroupiiminiproject.service.impl;

import com.practice.javagroupiiminiproject.model.entity.HabitLog;
import com.practice.javagroupiiminiproject.repository.HabitLogRepository;
import com.practice.javagroupiiminiproject.service.HabitLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public List<HabitLog> getHabitLogById(UUID habitId, Integer page, Integer size) {
        int offset = (page - 1) * size;

        return habitLogRepository.getHabitLogById(habitId, offset, size);
    }

}
