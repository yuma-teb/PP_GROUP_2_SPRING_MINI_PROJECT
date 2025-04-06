package com.practice.javagroupiiminiproject.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;

import com.practice.javagroupiiminiproject.model.entity.Habit;
import com.practice.javagroupiiminiproject.model.request.HabitRequest;

@Mapper
public interface HabitRepository {

   @Results(id = "HabitMapper", value = {
    })

   List<Habit> getAllHabits(Integer offset, Integer page);
    Habit getHabitById(Long habitId);
    Habit createHabit(HabitRequest habitRequest);
    Habit updateHabit(Long habitId, HabitRequest habitRequest);
    Habit deleteHabit(Long habitId);


}
