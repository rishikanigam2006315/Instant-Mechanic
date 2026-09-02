package com.instantmechanic.mechanic_api.service;

import com.instantmechanic.mechanic_api.model.ServiceRequest;
import com.instantmechanic.mechanic_api.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository repository;

    public ServiceRequestService(ServiceRequestRepository repository) {
        this.repository = repository;
    }

    public List<ServiceRequest> getAllRequests() {
        return repository.findAll();
    }

    public ServiceRequest createRequest(ServiceRequest request) {
        if (request.getId() == null) {
            request.setId(System.currentTimeMillis());
        }
        if (request.getStatus() == null || request.getStatus().isBlank()) {
            request.setStatus("Mechanic En Route");
        }
        if (request.getRequestTime() == null || request.getRequestTime().isBlank()) {
            request.setRequestTime("Today, Just now");
        }
        if (request.getCreatedAt() == null) {
            request.setCreatedAt(LocalDateTime.now());
        }

        return repository.save(request);
    }

    public ServiceRequest getRequestById(Long id) {
        ServiceRequest request = repository.findById(id);
        if (request == null) {
            throw new RuntimeException("Service request not found");
        }
        return request;
    }

    public ServiceRequest updateStatus(Long id, String status) {
        ServiceRequest updated = repository.updateStatus(id, status);
        if (updated == null) {
            throw new RuntimeException("Service request not found");
        }
        return updated;
    }
}
