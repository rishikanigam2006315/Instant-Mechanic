package com.instantmechanic.mechanic_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mechanic {

    private Long id;
    private String name;
    private Double rating;
    private String distance;
    private String location;
    private List<String> services;
    private Boolean isOpen;
    private String address;
    private String workingHours;
    private String phone;
}
