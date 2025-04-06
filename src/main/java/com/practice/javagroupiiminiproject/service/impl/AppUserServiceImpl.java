package com.practice.javagroupiiminiproject.service.impl;

import com.practice.javagroupiiminiproject.exception.BadRequestException;
import com.practice.javagroupiiminiproject.exception.NotFoundException;
import com.practice.javagroupiiminiproject.model.entity.AppUser;
import com.practice.javagroupiiminiproject.model.request.AppUserRequest;
import com.practice.javagroupiiminiproject.model.request.UserProfileRequest;
import com.practice.javagroupiiminiproject.model.response.AppUserResponse;
import com.practice.javagroupiiminiproject.model.response.UserProfileResponse;
import com.practice.javagroupiiminiproject.repository.AppUserRepository;
import com.practice.javagroupiiminiproject.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found with email: " + email);
        }
        System.out.println("User Details: " + user);
        if (!user.isVerified()) {
            throw new BadRequestException("User is not verified");
        }
        return user;
    }

    @Override
    public AppUserResponse register(AppUserRequest request) {
        // Check if the email or username already exists
        if (appUserRepository.checkExistEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists: " + request.getEmail());
        }
        if (appUserRepository.checkExistUsername(request.getFullName())) {
            throw new BadRequestException("Username already exists: " + request.getFullName());
        }

        // Create a new user
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String otp = generateOTP();
        LocalDateTime otpCreatedAt = LocalDateTime.now();
        LocalDate createdAt = LocalDate.now();

        AppUserRequest appUser = AppUserRequest.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(encodedPassword)
                .isVerified(false)
                .otp(otp)
                .otpCreatedAt(otpCreatedAt)
                .createdAt(createdAt)
                .build();

        // Save the user
        AppUser savedUser = appUserRepository.register(appUser);

        // Assign roles
        for (String role : request.getRoles()){
            if (role.equals("ROLE_USER")){
                appUserRepository.insertUserIdAndRoleId(1L, savedUser.getUserId());
            }
            if (role.equals("ROLE_ADMIN")){
                appUserRepository.insertUserIdAndRoleId(2L, savedUser.getUserId());
            }
        }

        // Send verification email
        emailService.sendVerificationEmail(savedUser.getEmail(), otp);

        // Return response
        return modelMapper.map(savedUser, AppUserResponse.class);
    }

    @Override
    @Transactional
    public void verify(String email, String otp) {
        AppUser user = appUserRepository.findByEmail(email);

        if (user == null) {
            throw new NotFoundException("User not found with email: " + email);
        } else if (user.isVerified()) {
            throw new BadRequestException("User is already verified");
        } else if (isOtpExpired(user.getOtpCreatedAt())) {
            throw new BadRequestException("OTP has expired");
        } else if (!otp.equals(user.getOtp())) {
            throw new BadRequestException("Invalid OTP");
        } else {
            appUserRepository.verifyUser(user.getUserId());
        }

    }


    @Override
    @Transactional
    public void resendOtp(String email) {
        // Find the user by email
        AppUser user = appUserRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found with email: " + email);
        }

        // Check if the user is already verified
        if (user.isVerified()) {
            throw new BadRequestException("User is already verified");
        }

        // Generate a new OTP and set the creation time
        String newOtp = generateOTP();
        LocalDateTime otpCreatedAt = LocalDateTime.now();

        // Update the user's OTP and OTP creation time in the database
        appUserRepository.updateOtpAndOtpCreatedAt(email, newOtp, otpCreatedAt);

        // Send the new OTP via email
        emailService.sendVerificationEmail(user.getEmail(), newOtp);
    }

    // Helper method to check if OTP is expired
    private boolean isOtpExpired(LocalDateTime otpCreatedAt) {
        if (otpCreatedAt == null) {
            return true; // No OTP exists
        }
        LocalDateTime expiryTime = otpCreatedAt.plusMinutes(5);
        return LocalDateTime.now().isAfter(expiryTime);
    }

    private String generateOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otpValue);
    }


    @Override
    public void requestPasswordReset(String email) {
        // Find the user by email
        AppUser user = appUserRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found with email: " + email);
        }

        // Generate a reset token and expiry time
        String resetToken = generateResetToken();
        LocalDateTime expiry = LocalDateTime.now().plusHours(1); // Token expires in 1 hour

        // Save the reset token and expiry in the database
        appUserRepository.updateResetToken(email, resetToken, expiry);

        // Send the reset email
        String resetLink = "http://your-frontend-url/reset-password?token=" + resetToken;
        emailService.sendResetPasswordEmail(user.getEmail(), resetLink);
    }

    private String generateResetToken() {
        return java.util.UUID.randomUUID().toString(); // Generate a random UUID as the reset token
    }

    @Override
    @Transactional
    public void resetPassword(String resetToken, String newPassword) {
        // Validate the reset token
        AppUser user = appUserRepository.findByResetToken(resetToken);
        if (user == null || !resetToken.equals(user.getResetToken())) {
            throw new BadRequestException("Invalid or expired reset token");
        }

        // Check if the token has expired
        if (LocalDateTime.now().isAfter(user.getResetTokenExpiry())) {
            throw new BadRequestException("Reset token has expired");
        }

        // Encode the new password and update it in the database
        String encodedPassword = passwordEncoder.encode(newPassword);
        appUserRepository.resetPassword(resetToken, encodedPassword);
    }

    @Override
    public UserProfileResponse getUserprofile() {
        return appUserRepository.getUserProfile(getCurrentUserEmail());
    }

    @Override
    public UserProfileResponse updateProfileUser(UserProfileRequest userProfileRequest) {
        return appUserRepository.updateProfileUser(userProfileRequest,getCurrentUserEmail());
    }

    @Override
    public void deleteProfileUser() {
        appUserRepository.deleteProfileUser(getCurrentUserEmail());
    }

    private String getCurrentUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}