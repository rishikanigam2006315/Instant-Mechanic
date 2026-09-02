package com.instantmechanic.mechanic_api.controller;

import com.instantmechanic.mechanic_api.model.ServiceRequest;
import com.instantmechanic.mechanic_api.service.ServiceRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    private final ServiceRequestService service;

    public ServiceRequestController(ServiceRequestService service) {
        this.service = service;
    }

    @GetMapping
    public List<ServiceRequest> getAllRequests() {
        return service.getAllRequests();
    }

    @PostMapping
    public ServiceRequest createRequest(@RequestBody ServiceRequest request) {
        return service.createRequest(request);
    }

    @GetMapping("/{id}")
    public ServiceRequest getRequestById(@PathVariable Long id) {
        return service.getRequestById(id);
    }

    @PatchMapping("/{id}/status")
    public ServiceRequest updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }
}
