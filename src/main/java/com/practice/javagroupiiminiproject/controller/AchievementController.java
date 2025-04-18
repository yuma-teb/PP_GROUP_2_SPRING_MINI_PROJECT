package com.practice.javagroupiiminiproject.controller;

import com.practice.javagroupiiminiproject.model.entity.Achievement;
import com.practice.javagroupiiminiproject.model.response.APIResponse;
import com.practice.javagroupiiminiproject.service.AchievementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/achievement")
public class AchievementController {
    private final AchievementService service;
    @GetMapping
    public ResponseEntity<APIResponse<List<Achievement>>> getAllAchievement(){
        return service.getAllAchievement();
    }
    @GetMapping("/{app-user}")
    public ResponseEntity<APIResponse<List<Achievement>>> getAchievementByAppUserID (@PathVariable("app-user") UUID appuserId){
        return service.getAchievementByAppUserID(appuserId);
    }
}
