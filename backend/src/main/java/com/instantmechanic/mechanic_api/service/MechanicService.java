package com.instantmechanic.mechanic_api.service;

import com.instantmechanic.mechanic_api.model.Mechanic;
import com.instantmechanic.mechanic_api.repository.MechanicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    public MechanicService(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }

    public Mechanic getMechanicById(Long id) {

        Mechanic mechanic = mechanicRepository.findById(id);

        if (mechanic == null) {
            throw new RuntimeException("Mechanic not found");
        }

        return mechanic;
    }
    public List<Mechanic> searchMechanics(String name) {
        return mechanicRepository.searchByName(name);
    }

    public List<Mechanic> filterMechanicsByService(String service) {
        return mechanicRepository.filterByService(service);
    }
}