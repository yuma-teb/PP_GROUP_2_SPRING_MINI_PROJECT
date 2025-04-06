package com.practice.javagroupiiminiproject.service.impl;

import com.practice.javagroupiiminiproject.model.entity.Achievement;
import com.practice.javagroupiiminiproject.model.response.APIResponse;
import com.practice.javagroupiiminiproject.repository.AchievementRepository;
import com.practice.javagroupiiminiproject.service.AchievementService;
import jakarta.servlet.Servlet;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {
    private final AchievementRepository repository;
    private final Servlet servlet;

    @Override
    public ResponseEntity<APIResponse<List<Achievement>>> getAllAchievement() {
        List<Achievement> achievements = repository.getAllAchievement();

        APIResponse<List<Achievement>> response = new APIResponse<>(true,"Retriev All Achievement Successfully", HttpStatus.OK,
                achievements, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public Achievement getAchievementByAppUserID(UUID appuserId) {
        Achievement achievement = repository.getAchievementByAppUserID(appuserId);
        return null;
    }
}
