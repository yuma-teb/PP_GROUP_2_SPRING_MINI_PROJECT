package com.practice.javagroupiiminiproject.controller;
import com.practice.javagroupiiminiproject.model.request.UserProfileRequest;
import com.practice.javagroupiiminiproject.model.response.APIResponse;
import com.practice.javagroupiiminiproject.model.response.UserProfileResponse;
import com.practice.javagroupiiminiproject.service.AppUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/profiles")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final AppUserService appUserService;

    public ProfileController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        UserProfileResponse profileResponseAPIResponse = appUserService.getUserprofile();
        APIResponse<Object> response = APIResponse.builder()
                .success(true)
                .message("Get user profile successfully")
                .status(HttpStatus.OK)
                .payload(profileResponseAPIResponse)
                .timestamps(LocalDateTime.now()).build();
        return ResponseEntity.ok(response);
    }
    @PutMapping()
    public ResponseEntity<?> updateProfileUser(@Valid @RequestBody UserProfileRequest userProfileRequest) {
        UserProfileResponse profileResponse = appUserService.updateProfileUser(userProfileRequest);
        APIResponse<Object> response = APIResponse.builder()
                .success(true)
                .message("Update user profile sucessfully")
                .status(HttpStatus.OK)
                .payload(profileResponse)
                .timestamps(LocalDateTime.now()).build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteProfileUser(){
        appUserService.deleteProfileUser();
        APIResponse<Object> response = APIResponse.builder()
                .success(true)
                .message("Deleted user profile sucessfully")
                .status(HttpStatus.OK)
                .payload(null)
                .timestamps(LocalDateTime.now()).build();
        return ResponseEntity.ok(response);
    }

}
