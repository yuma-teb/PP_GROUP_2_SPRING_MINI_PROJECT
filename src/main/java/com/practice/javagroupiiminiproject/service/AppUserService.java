package com.practice.javagroupiiminiproject.service;

import com.practice.javagroupiiminiproject.model.request.AppUserRequest;
import com.practice.javagroupiiminiproject.model.response.AppUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    AppUserResponse register(AppUserRequest request);
    void verify(String email, String otp);

    void resendOtp(String email);

    void requestPasswordReset(String email);

    void resetPassword(String resetToken, String newPassword);
}
