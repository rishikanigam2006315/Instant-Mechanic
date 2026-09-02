package com.instantmechanic.mechanic_api.repository;

import com.instantmechanic.mechanic_api.model.Mechanic;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class MechanicRepository {

    private final List<Mechanic> mechanics = Arrays.asList(
            new Mechanic(
                    1L,
                    "Apex Auto Care & 24/7 Garage",
                    4.9,
                    "1.2 km away",
                    "Indiranagar, 100ft Road",
                    Arrays.asList(
                            "Engine Diagnostics",
                            "Brake Overhaul",
                            "Full Periodic Service",
                            "24/7 Towing",
                            "Battery Jumpstart",
                            "AC Gas & Cooling"
                    ),
                    true,
                    "Plot #42, Opposite Metro Pillar 84, Indiranagar, Bengaluru",
                    "24/7 Emergency & 8:00 AM - 10:30 PM",
                    "+91 98765 43210"
            ),
            new Mechanic(
                    2L,
                    "Speedy Wheels Express Hub",
                    4.8,
                    "2.4 km away",
                    "Koramangala 5th Block",
                    Arrays.asList(
                            "Tyre Replacement",
                            "Wheel Alignment",
                            "Tubeless Puncture Fix",
                            "Oil Change & Filter",
                            "Brake Pad Replacement"
                    ),
                    true,
                    "No. 18, Near Sony World Signal, Koramangala 5th Block, Bengaluru",
                    "8:30 AM - 9:30 PM (Daily)",
                    "+91 98765 43211"
            ),
            new Mechanic(
                    3L,
                    "RoadRescue Instant Towing & SOS",
                    4.9,
                    "0.8 km away",
                    "Outer Ring Road Junction",
                    Arrays.asList(
                            "24/7 Towing",
                            "Emergency Jumpstart",
                            "Fuel On Delivery",
                            "Lockout Assistance",
                            "Accident Recovery"
                    ),
                    true,
                    "Beside Bellandur Flyover, Outer Ring Road, Bengaluru",
                    "24 Hours Everyday",
                    "+91 98765 43212"
            ),
            new Mechanic(
                    4L,
                    "Precision German & Multi-Brand Garage",
                    4.9,
                    "4.1 km away",
                    "Whitefield Tech Park Road",
                    Arrays.asList(
                            "Engine Diagnostics",
                            "ECU Scanning",
                            "Transmission Overhaul",
                            "Suspension Repair",
                            "Premium Detailing"
                    ),
                    true,
                    "Unit 7, ITPL Main Road, Whitefield, Bengaluru",
                    "9:00 AM - 8:00 PM",
                    "+91 98765 43213"
            ),
            new Mechanic(
                    5L,
                    "Two-Wheeler & Superbike Pitstop",
                    4.7,
                    "3.2 km away",
                    "HSR Layout Sector 2",
                    Arrays.asList(
                            "Bike Engine Tuning",
                            "Chain Lubing & Sprocket",
                            "Carburetor / EFI Cleaning",
                            "Disc Brake Service",
                            "Puncture Repair"
                    ),
                    true,
                    "27th Main, Sector 2, HSR Layout, Bengaluru",
                    "9:00 AM - 9:00 PM",
                    "+91 98765 43214"
            ),
            new Mechanic(
                    6L,
                    "QuickFix Auto Works",
                    4.6,
                    "5.5 km away",
                    "BTM 2nd Stage",
                    Arrays.asList(
                            "General Service",
                            "Oil Change & Filter",
                            "Battery Jumpstart",
                            "Electrical & Wiring Fix"
                    ),
                    false,
                    "16th Main Road, BTM 2nd Stage, Bengaluru",
                    "9:30 AM - 8:30 PM (Opens 9:30 AM tomorrow)",
                    "+91 98765 43215"
            )
    );

    public List<Mechanic> findAll() {
        return mechanics;
    }

    public Mechanic findById(Long id) {
        return mechanics.stream()
                .filter(mechanic -> mechanic.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Mechanic> searchByName(String query) {
        if (query == null || query.isBlank()) {
            return mechanics;
        }
        String q = query.toLowerCase().trim();
        return mechanics.stream()
                .filter(mechanic ->
                        mechanic.getName().toLowerCase().contains(q) ||
                        mechanic.getLocation().toLowerCase().contains(q) ||
                        mechanic.getServices().stream().anyMatch(s -> s.toLowerCase().contains(q)))
                .toList();
    }

    public List<Mechanic> filterByService(String service) {
        if (service == null || service.isBlank() || service.equalsIgnoreCase("All")) {
            return mechanics;
        }
        String sLower = service.toLowerCase().trim();
        return mechanics.stream()
                .filter(mechanic ->
                        mechanic.getServices().stream()
                                .anyMatch(s -> s.toLowerCase().contains(sLower) || sLower.contains(s.toLowerCase())))
                .toList();
    }
}
