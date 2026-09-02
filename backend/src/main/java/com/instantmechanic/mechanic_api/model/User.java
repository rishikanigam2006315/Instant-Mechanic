package com.instantmechanic.mechanic_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String vehicleType;
    private String vehicleNumber;
    private String password;
    private String role;
}
