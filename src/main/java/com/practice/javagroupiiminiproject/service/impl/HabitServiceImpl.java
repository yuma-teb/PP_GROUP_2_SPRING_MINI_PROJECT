package com.practice.javagroupiiminiproject.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.javagroupiiminiproject.model.entity.Habit;
import com.practice.javagroupiiminiproject.model.request.HabitRequest;
import com.practice.javagroupiiminiproject.service.HabitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService{

  @Override
  public List<Habit> getAllHabits(Integer offset, Integer page) {
    
    return null;
  }

  @Override
  public Habit getHabitById(Long habitId) {
    
    return null;
  }

  @Override
  public Habit createHabit(HabitRequest habitRequest) {
    
    return null;
  }

  @Override
  public Habit updateHabit(Long habitId, HabitRequest habitRequest) {
    
    return null;
  }

  @Override
  public Habit deleteHabit(Long habitId) {
    
    return null;
  }


}
