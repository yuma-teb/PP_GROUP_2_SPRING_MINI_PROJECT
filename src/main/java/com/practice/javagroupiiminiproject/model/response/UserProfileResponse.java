package com.practice.javagroupiiminiproject.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private UUID id;
    private String fullName;
    private String email;
    private boolean isVerified;
    private int level;
    private int experience;
    private String profileImage;
    private LocalDate createdAt;
}
