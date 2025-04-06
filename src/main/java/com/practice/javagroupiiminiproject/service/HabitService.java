package com.practice.javagroupiiminiproject.service;

import java.util.List;

import com.practice.javagroupiiminiproject.model.entity.Habit;
import com.practice.javagroupiiminiproject.model.request.HabitRequest;

public interface HabitService {

   List<Habit> getAllHabits(Integer offset, Integer page);
    Habit getHabitById(Long habitId);
    Habit createHabit(HabitRequest habitRequest);
    Habit updateHabit(Long habitId, HabitRequest habitRequest);
    Habit deleteHabit(Long habitId);

}
