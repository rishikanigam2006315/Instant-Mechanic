package com.instantmechanic.mechanic_api.service;

import com.instantmechanic.mechanic_api.dto.AuthResponse;
import com.instantmechanic.mechanic_api.dto.LoginRequest;
import com.instantmechanic.mechanic_api.dto.PasswordResetRequest;
import com.instantmechanic.mechanic_api.dto.SignUpRequest;
import com.instantmechanic.mechanic_api.model.User;
import com.instantmechanic.mechanic_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse login(LoginRequest request) {
        if (request.getEmailOrPhone() == null || request.getEmailOrPhone().isBlank()) {
            return new AuthResponse(false, "Please provide email or phone number", null, null);
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            return new AuthResponse(false, "Please provide your password", null, null);
        }

        Optional<User> optionalUser = userRepository.findByEmailOrPhone(request.getEmailOrPhone());
        if (optionalUser.isEmpty()) {
            // If logging in as demo user or guest, fallback or error
            return new AuthResponse(false, "No account found with this email or phone number", null, null);
        }

        User user = optionalUser.get();
        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(false, "Incorrect password. Please try again.", null, null);
        }

        return new AuthResponse(true, "Welcome back, " + user.getName(), user, "token-" + user.getId() + "-" + System.currentTimeMillis());
    }

    public AuthResponse signUp(SignUpRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return new AuthResponse(false, "Email is required", null, null);
        }
        if (request.getPassword() == null || request.getPassword().length() < 4) {
            return new AuthResponse(false, "Password must be at least 4 characters", null, null);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(false, "An account with this email already exists", null, null);
        }

        User newUser = new User(
                null,
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getVehicleType() != null ? request.getVehicleType() : "Car",
                request.getVehicleNumber() != null ? request.getVehicleNumber() : "KA 01 MJ 4521",
                request.getPassword(),
                "CUSTOMER"
        );

        User saved = userRepository.save(newUser);
        return new AuthResponse(true, "Registration successful", saved, "token-" + saved.getId() + "-" + System.currentTimeMillis());
    }

    public AuthResponse sendResetOtp(String emailOrPhone) {
        if (emailOrPhone == null || emailOrPhone.isBlank()) {
            return new AuthResponse(false, "Please provide email or phone number", null, null);
        }

        Optional<User> optionalUser = userRepository.findByEmailOrPhone(emailOrPhone);
        if (optionalUser.isEmpty()) {
            return new AuthResponse(false, "No user found with " + emailOrPhone, null, null);
        }

        return new AuthResponse(true, "A 4-digit verification code has been sent to " + emailOrPhone, optionalUser.get(), null);
    }

    public AuthResponse resetPassword(PasswordResetRequest request) {
        if (request.getEmailOrPhone() == null || request.getEmailOrPhone().isBlank()) {
            return new AuthResponse(false, "Email or phone number is required", null, null);
        }
        if (request.getNewPassword() == null || request.getNewPassword().length() < 4) {
            return new AuthResponse(false, "New password must be at least 4 characters", null, null);
        }

        Optional<User> optionalUser = userRepository.findByEmailOrPhone(request.getEmailOrPhone());
        if (optionalUser.isEmpty()) {
            return new AuthResponse(false, "User not found", null, null);
        }

        User user = optionalUser.get();
        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        return new AuthResponse(true, "Password updated successfully! You can now log in.", user, null);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
