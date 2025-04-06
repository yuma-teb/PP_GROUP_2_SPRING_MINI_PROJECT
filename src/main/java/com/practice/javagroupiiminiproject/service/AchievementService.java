package com.practice.javagroupiiminiproject.service;

import com.practice.javagroupiiminiproject.model.entity.Achievement;
import com.practice.javagroupiiminiproject.model.response.APIResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AchievementService {
    ResponseEntity<APIResponse<List<Achievement>>> getAllAchievement();

    Achievement getAchievementByAppUserID(UUID appuserId);
}
