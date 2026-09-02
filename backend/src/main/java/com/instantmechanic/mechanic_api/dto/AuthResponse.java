package com.instantmechanic.mechanic_api.dto;

import com.instantmechanic.mechanic_api.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private boolean success;
    private String message;
    private User user;
    private String token;
}
