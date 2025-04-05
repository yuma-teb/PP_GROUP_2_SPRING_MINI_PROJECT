package com.practice.javagroupiiminiproject.repository;

import com.practice.javagroupiiminiproject.model.entity.HabitLog;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface HabitLogRepository {

    @Results(id = "habitLogMapper", value = {
            @Result(property = "habitLogId", column = "habit_log_id"),
            @Result(property = "logDate", column = "log_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "xpEarned", column = "experience_earned"),
            @Result(property = "habitId", column = "habit_id")
    })
    @Select("SELECT * FROM habit_logs WHERE habit_log_id = #{id}")
    HabitLog findById(UUID id);

    @ResultMap("habitLogMapper")
    @Select("""
            INSERT INTO habit_logs (log_date, status, experience_earned, habit_id)
            VALUES (#{request.logDate}, #{request.status}, #{request.xpEarned}, #{request.habitId})
            RETURNING *;
            """)
    HabitLog saveHabitLog(@Param("request") HabitLog habitLog);
}