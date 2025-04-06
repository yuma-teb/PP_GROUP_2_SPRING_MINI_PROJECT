package com.practice.javagroupiiminiproject.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.practice.javagroupiiminiproject.model.entity.Habit;
import com.practice.javagroupiiminiproject.model.request.HabitRequest;

@Mapper
public interface HabitRepository {

   @Results(id = "HabitMapper", value = {
        @Result(property = "habitId", column = "habit_id"),
        @Result(property = "title", column = "title"),
        @Result(property = "description", column = "description"),
        @Result(property = "frequency", column = "frequency"),
        @Result(property = "isActive", column = "is_active"),
        @Result(property = "appUser", column = "app_user_id" , one = @One(select = "com.practice.javagroupiiminiproject.repository.AppUserRepository.getAppUserById")),
        @Result(property = "createdAt", column = "created_at")
    })
    @Select("""
        SELECT * FROM habits OFFSET #{offset} LIMIT #{page};
        """)
   List<Habit> getAllHabits(Integer offset, Integer page);

   @ResultMap("HabitMapper")
   @Select("""
        SELECT * FROM habits WHERE habit_id = #{habitId};
        """)
    Habit getHabitById(Long habitId);


    @ResultMap("HabitMapper")
    @Select(""" 
        INSERT INTO habits (title, description, frequency) VALUES (#{req.title}, #{req.description}, #{req.frequency}, true) RETURNING *;
        """)
    Habit createHabit(@Param("req") HabitRequest habitRequest);

    @ResultMap("HabitMapper")
    @Select("""
        UPDATE habits SET title = #{req.title}, description = #{req.description}, frequency = #{req.frequency} WHERE habit_id = #{habitId} RETURNING *;
        """)
    Habit updateHabit(Long habitId,@Param("req") HabitRequest habitRequest);


    @ResultMap("HabitMapper")
    @Select("""
        DELETE FROM habits WHERE habit_id = #{habitId} RETURNING *;
        """)
    Habit deleteHabit(Long habitId);


}
