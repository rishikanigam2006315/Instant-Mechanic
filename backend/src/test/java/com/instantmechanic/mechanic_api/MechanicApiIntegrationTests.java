package com.instantmechanic.mechanic_api;

import com.instantmechanic.mechanic_api.dto.AuthResponse;
import com.instantmechanic.mechanic_api.dto.LoginRequest;
import com.instantmechanic.mechanic_api.dto.SignUpRequest;
import com.instantmechanic.mechanic_api.model.Mechanic;
import com.instantmechanic.mechanic_api.model.ServiceRequest;
import com.instantmechanic.mechanic_api.service.AuthService;
import com.instantmechanic.mechanic_api.service.MechanicService;
import com.instantmechanic.mechanic_api.service.ServiceRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MechanicApiIntegrationTests {

    @Autowired
    private MechanicService mechanicService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ServiceRequestService serviceRequestService;

    @Test
    void testGetAllMechanics() {
        List<Mechanic> mechanics = mechanicService.getAllMechanics();
        assertNotNull(mechanics);
        assertEquals(6, mechanics.size(), "Should have the 6 verified Bangalore garages matching UI");
        assertEquals("Apex Auto Care & 24/7 Garage", mechanics.get(0).getName());
    }

    @Test
    void testSearchAndFilterMechanics() {
        // Search by location / name
        List<Mechanic> searched = mechanicService.searchMechanics("Indiranagar");
        assertFalse(searched.isEmpty());
        assertTrue(searched.stream().anyMatch(m -> m.getName().contains("Apex Auto Care")));

        // Filter by service
        List<Mechanic> towingGarages = mechanicService.filterMechanicsByService("Towing");
        assertFalse(towingGarages.isEmpty());
        assertTrue(towingGarages.stream().anyMatch(m -> m.getName().contains("RoadRescue")));
    }

    @Test
    void testAuthDemoLogin() {
        // Demo login matching frontend 1-tap demo credentials
        LoginRequest loginRequest = new LoginRequest("rahul.sharma@example.com", "Password@123");
        AuthResponse response = authService.login(loginRequest);
        assertTrue(response.isSuccess());
        assertNotNull(response.getUser());
        assertEquals("Rahul Sharma", response.getUser().getName());
        assertEquals("KA 01 MJ 4521", response.getUser().getVehicleNumber());
    }

    @Test
    void testAuthSignUp() {
        SignUpRequest signUpRequest = new SignUpRequest(
                "Amit Kumar",
                "amit.kumar@example.com",
                "+91 99887 76655",
                "Two-Wheeler",
                "KA 04 H 1234",
                "Password@123"
        );
        AuthResponse response = authService.signUp(signUpRequest);
        assertTrue(response.isSuccess());
        assertEquals("Amit Kumar", response.getUser().getName());
    }

    @Test
    void testServiceRequestsFlow() {
        // 1. Preloaded requests matching UI
        List<ServiceRequest> initial = serviceRequestService.getAllRequests();
        assertNotNull(initial);
        assertTrue(initial.size() >= 2);

        // 2. Create new service request
        ServiceRequest newReq = new ServiceRequest();
        newReq.setCustomerName("Priya Singh");
        newReq.setPhoneNumber("+91 91234 56789");
        newReq.setVehicleNumber("KA 03 AB 9999");
        newReq.setService("Battery Jumpstart");
        newReq.setProblemDescription("Dead battery in office parking");
        newReq.setMechanicName("Speedy Wheels Express Hub");

        ServiceRequest created = serviceRequestService.createRequest(newReq);
        assertNotNull(created.getId());
        assertEquals("Priya Singh", created.getCustomerName());
        assertEquals("Mechanic En Route", created.getStatus());
        assertEquals("Today, Just now", created.getRequestTime());

        // 3. Status update
        ServiceRequest updated = serviceRequestService.updateStatus(created.getId(), "Completed");
        assertEquals("Completed", updated.getStatus());
    }
}
