package com.instantmechanic.mechanic_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequest {

    private Long id;
    private String customerName;
    private String phoneNumber;
    private String vehicleNumber;
    private String service;
    private String problemDescription;
    private String mechanicName;
    private String status;
    private String requestTime;
    private LocalDateTime createdAt;
}