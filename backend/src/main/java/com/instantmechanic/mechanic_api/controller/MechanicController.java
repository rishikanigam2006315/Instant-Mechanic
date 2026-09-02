package com.instantmechanic.mechanic_api.controller;

import com.instantmechanic.mechanic_api.model.Mechanic;
import com.instantmechanic.mechanic_api.service.MechanicService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicController {

    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @GetMapping
    public List<Mechanic> getAllMechanics() {
        return mechanicService.getAllMechanics();
    }

    @GetMapping("/{id}")
    public Mechanic getMechanicById(@PathVariable Long id) {
        return mechanicService.getMechanicById(id);
    }
    @GetMapping("/search")
    public List<Mechanic> searchMechanics(
            @RequestParam String name) {

        return mechanicService.searchMechanics(name);
    }

    @GetMapping("/filter")
    public List<Mechanic> filterByService(
            @RequestParam String service) {

        return mechanicService.filterMechanicsByService(service);
    }
}