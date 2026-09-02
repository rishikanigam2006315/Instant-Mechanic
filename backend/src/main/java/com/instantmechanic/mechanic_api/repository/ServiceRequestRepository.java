package com.instantmechanic.mechanic_api.repository;

import com.instantmechanic.mechanic_api.model.ServiceRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ServiceRequestRepository {

    private final List<ServiceRequest> requests = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(200);

    public ServiceRequestRepository() {
        // Pre-populate with initial booking history matching the UI
        requests.add(new ServiceRequest(
                101L,
                "Rahul Sharma",
                "+91 98765 12345",
                "KA 01 MJ 4521",
                "24/7 Towing",
                "Engine stalled in heavy traffic, battery indicator flashing.",
                "RoadRescue Instant Towing & SOS",
                "Mechanic En Route",
                "Today, 15 mins ago",
                LocalDateTime.now().minusMinutes(15)
        ));

        requests.add(new ServiceRequest(
                102L,
                "Rahul Sharma",
                "+91 98765 12345",
                "KA 01 MJ 4521",
                "Full Periodic Service",
                "Standard 20,000 km general inspection and brake fluid replacement.",
                "Apex Auto Care & 24/7 Garage",
                "Completed",
                "24 Aug 2026",
                LocalDateTime.now().minusDays(10)
        ));
    }

    public List<ServiceRequest> findAll() {
        return new ArrayList<>(requests);
    }

    public ServiceRequest findById(Long id) {
        return requests.stream()
                .filter(request -> request.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public ServiceRequest save(ServiceRequest request) {
        if (request.getId() == null) {
            request.setId(idGenerator.incrementAndGet());
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

        // Add to top of list so newest requests appear first
        requests.add(0, request);
        return request;
    }

    public ServiceRequest updateStatus(Long id, String status) {
        ServiceRequest existing = findById(id);
        if (existing != null) {
            existing.setStatus(status);
            return existing;
        }
        return null;
    }
}
