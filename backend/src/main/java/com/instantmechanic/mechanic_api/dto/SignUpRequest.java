package com.instantmechanic.mechanic_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String name;
    private String email;
    private String phone;
    private String vehicleType;
    private String vehicleNumber;
    private String password;
}
