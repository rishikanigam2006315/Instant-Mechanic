package com.instantmechanic.mechanic_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
    private String emailOrPhone;
    private String otp;
    private String newPassword;
}
