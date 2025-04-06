package com.practice.javagroupiiminiproject.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.practice.javagroupiiminiproject.model.entity.Habit;
import com.practice.javagroupiiminiproject.model.request.HabitRequest;

@Mapper
public interface HabitRepository {

  @Results(id = "habitMapper", value = {
            @Result(property = "habitId", column = "habit_id"),
            @Result(property = "title" , column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "frequency" , column = "frequency"),
            @Result(property = "isActive" , column = "is_active"),
            @Result(property = "createAt" , column = "created_at"),     
    })

    @Select("""
       SELECT * FROM habits OFFSET #{offset} LIMIT #{size};
   """)
    List<Habit> getAllHabits(Integer offset, Integer size);


    @ResultMap("habitMapper")
    @Select("""      
         INSERT INTO habits (title, description, frequency, is_active, create_at)
   VALUES (#{req.title}, #{req.description}, #{req.frequency}, #{req.isActive},
    #{req.createAt})
      """)
    Habit createHabit(@Param("req") HabitRequest habitRequest);


    @ResultMap("habitMapper")
    @Select("""
        SELECT  * FROM habits WHERE habit_id = #{habitId};
    """)
    Habit getHabitById(Long habitId);


    @ResultMap("habitMapper")
    @Select("""
       UPDATE habits SET title = #{req.title}, description = #{req.description}, frequency = #{req.frequency}, is_active = #{req.isActive},
       WHERE habit_id = #{habitId}
RETURNING *;
    """)
    Habit updateHabitById(Long habitId,@Param("req")  HabitRequest habitRequest);

    @ResultMap("habitMapper")
    @Select("""
     DELETE FROM habits WHERE habit_id = #{habitId};
     """)
    Habit deleteHabitById(Long habitId);
}


