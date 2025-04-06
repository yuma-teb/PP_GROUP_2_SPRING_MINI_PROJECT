package com.practice.javagroupiiminiproject.repository;

import com.practice.javagroupiiminiproject.model.entity.AppUser;
import com.practice.javagroupiiminiproject.model.request.AppUserRequest;
import com.practice.javagroupiiminiproject.model.request.UserProfileRequest;
import com.practice.javagroupiiminiproject.model.response.UserProfileResponse;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
public interface AppUserRepository {

    @Results(id = "appUserMapper", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "isVerified", column = "is_verified"),
            @Result(property = "otp", column = "otp"),
            @Result(property = "otpCreatedAt", column = "otp_created_at"),
            @Result(property = "resetToken", column = "reset_token"),
            @Result(property = "resetTokenExpiry", column = "reset_token_expiry"),
            @Result(property = "level", column = "level"),
            @Result(property = "experience", column = "experience"),
            @Result(property = "profile_image", column = "profile_image"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "roles", column = "user_id",
                    many = @Many(select = "com.example.jwt.repository.AppUserRepository.getAllRolesByUserId"))
    })
    @Select("SELECT * FROM app_users WHERE email = #{email}")
    AppUser findByEmail(String email);

    @Select("SELECT COUNT(*) > 0 FROM app_users WHERE email = #{email}")
    boolean checkExistEmail(String email);

    @Select("SELECT COUNT(*) > 0 FROM app_users WHERE full_name = #{fullName}")
    boolean checkExistUsername(String fullName);

    @ResultMap("appUserMapper")
    @Select("""
            INSERT INTO app_users (full_name, email, password, is_verified, otp, otp_created_at, created_at)
            VALUES (#{fullName}, #{email}, #{password}, false, #{otp}, #{otpCreatedAt}, #{createdAt})
            RETURNING *
            """)
    AppUser register(AppUserRequest appUser);

    @Update("UPDATE app_users SET otp = #{otp}, otp_created_at = #{otpCreatedAt} WHERE email = #{email}")
    void updateOtpAndOtpCreatedAt(String email, String otp, LocalDateTime otpCreatedAt);

    @Select("""
        SELECT name FROM user_role UR
        INNER JOIN app_roles AR ON UR.role_id = AR.role_id
        WHERE user_id = CAST(#{user_id} AS UUID)
    """)
    List<String> getAllRolesByUserId(@Param("user_id") UUID userId); // Ensure this is UUID

    @Insert("INSERT INTO user_role (user_id, role_id) VALUES (CAST(#{userId} AS UUID), #{roleId})")
    void insertUserIdAndRoleId(@Param("roleId") Long roleId, @Param("userId") UUID userId); // Ensure this is UUID

    @Update("UPDATE app_users SET is_verified = true, otp = NULL, otp_created_at = NULL WHERE user_id = CAST(#{userId} AS UUID)")
    void verifyUser(@Param("userId") UUID userId); // Ensure this is UUID

    @Update("UPDATE app_users SET reset_token = #{resetToken}, reset_token_expiry = #{expiry} WHERE email = #{email}")
    void updateResetToken(String email, String resetToken, LocalDateTime expiry);

    @Select("SELECT * FROM app_users WHERE reset_token = #{resetToken}")
    AppUser findByResetToken(String resetToken);

    @Update("UPDATE app_users SET password = #{newPassword}, reset_token = NULL, reset_token_expiry = NULL WHERE reset_token = #{resetToken}")
    void resetPassword(String resetToken, String newPassword);

    //User Profile
    @Select("""
SELECT * FROM app_users WHERE email = #{email}
""")
    @Results(id = "profileMap",value = {
            @Result(property = "id",column = "app_user_id"),
            @Result(property = "isVerified",column = "is_verified"),
            @Result(property = "experience",column = "xp"),
            @Result(property = "profileImage",column = "profile_image_url"),
            @Result(property = "createdAt",column = "created_at")
    })
    UserProfileResponse getUserProfile(String email);

    @Update("""
UPDATE app_users SET username = #{req.username} , profile_image_url = #{req.profileImage} WHERE email = #{email}
""")
    @ResultMap("profileMap")
    void updateProfileUser(@Param("req") UserProfileRequest userProfileRequest, String email);

    @Delete("""
DELETE FROM app_users WHERE email = #{currentUserEmail}
""")
    void deleteProfileUser(String currentUserEmail);
}