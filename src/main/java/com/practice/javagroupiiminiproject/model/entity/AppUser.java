package com.practice.javagroupiiminiproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    private UUID appUserId;
    private String username;
    private String email;
    private String password;
    private String level;
    private String experience;
    private String profile_image;
    private boolean isVerified;
    private LocalDate createdAt;

}
