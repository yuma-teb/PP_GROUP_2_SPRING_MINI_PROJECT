package com.practice.javagroupiiminiproject.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.javagroupiiminiproject.exception.NotFoundException;
import com.practice.javagroupiiminiproject.model.entity.Habit;
import com.practice.javagroupiiminiproject.model.request.HabitRequest;
import com.practice.javagroupiiminiproject.repository.HabitRepository;
import com.practice.javagroupiiminiproject.service.HabitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService{

  private final HabitRepository habitRepository;

  @Override
  public List<Habit> getAllHabits(Integer offset, Integer page) {
    List<Habit> habits = habitRepository.getAllHabits(offset, page);
    if (habits.isEmpty()) {
      throw new NotFoundException("No habits found");
    }
    return habits;
  }

  @Override
  public Habit getHabitById(Long habitId) {
    Habit habit = habitRepository.getHabitById(habitId);
    if (habit == null) {
      throw new NotFoundException("Habit not found with ID: " + habitId);
    }
    return habit  ;
  }

  @Override
  public Habit createHabit(HabitRequest habitRequest) {
    return habitRepository.createHabit(habitRequest);
  }

  @Override
  public Habit updateHabit(Long habitId, HabitRequest habitRequest) {
    getHabitById(habitId); 
    return habitRepository.updateHabitById(habitId, habitRequest);
  }

  @Override
  public Habit deleteHabit(Long habitId) {
    getHabitById(habitId); 
    return habitRepository.deleteHabitById(habitId);
  }
}
