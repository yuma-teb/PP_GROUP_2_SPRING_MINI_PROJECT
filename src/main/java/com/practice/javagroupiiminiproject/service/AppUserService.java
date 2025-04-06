package com.practice.javagroupiiminiproject.service;

import com.practice.javagroupiiminiproject.model.request.AppUserRequest;
import com.practice.javagroupiiminiproject.model.request.UserProfileRequest;
import com.practice.javagroupiiminiproject.model.response.AppUserResponse;
import com.practice.javagroupiiminiproject.model.response.UserProfileResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    AppUserResponse register(AppUserRequest request);
    void verify(String email, String otp);

    void resendOtp(String email);

    void requestPasswordReset(String email);

    void resetPassword(String resetToken, String newPassword);

    UserProfileResponse getUserprofile();

    UserProfileResponse updateProfileUser(UserProfileRequest userProfileRequest);

    void deleteProfileUser();
}
