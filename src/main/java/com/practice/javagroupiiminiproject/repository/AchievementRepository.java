package com.practice.javagroupiiminiproject.repository;

import com.practice.javagroupiiminiproject.model.entity.Achievement;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Mapper
public interface AchievementRepository {
    @Results(id = "achievementMapper",value = {
            @Result(property = "achievementId",column = "achievement_id"),
            @Result(property = "exRequired",column = "experience_required"),

    })

        @Select("""
        SELECT * FROM achievements;
        """)

    List<Achievement> getAllAchievement();

    @ResultMap("achievementMapper")
    @Select("""
        SELECT * FROM app_user_achievements auc INNER JOIN public.achievements a on a.achievement_id = auc.achievement_id WHERE app_user_id = #{appuserId};
       
       """)
    List <Achievement> getAchievementByAppUserID(UUID appuserId);
}
