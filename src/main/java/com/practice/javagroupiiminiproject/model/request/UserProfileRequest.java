package com.practice.javagroupiiminiproject.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequest {
    @NotBlank(message = "Username cannot be blank")
    private String fullName;
    private String profileImage;
}
