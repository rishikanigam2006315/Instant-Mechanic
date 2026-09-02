package com.example.instantmechanic

import com.example.instantmechanic.data.model.ServiceRequest
import com.example.instantmechanic.data.repository.MechanicRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MechanicRepositoryTest {

    private lateinit var repository: MechanicRepository

    @Before
    fun setUp() {
        repository = MechanicRepository()
    }

    @Test
    fun getMechanics_returnsVerifiedGarages() = runBlocking {
        val mechanics = repository.getMechanics()
        assertNotNull(mechanics)
        assertTrue("Should contain sample verified garages", mechanics.isNotEmpty())
        assertEquals(6, mechanics.size)
        assertEquals("Apex Auto Care & 24/7 Garage", mechanics[0].name)
    }

    @Test
    fun getMechanicById_returnsCorrectGarage() = runBlocking {
        val mechanic = repository.getMechanicById(1L)
        assertNotNull(mechanic)
        assertEquals(1L, mechanic.id)
        assertEquals("Indiranagar, 100ft Road", mechanic.location)
    }

    @Test
    fun searchMechanics_byNameOrLocation_returnsFilteredResults() = runBlocking {
        // Search by location
        val indiranagarGarages = repository.searchMechanics("Indiranagar")
        assertFalse(indiranagarGarages.isEmpty())
        assertTrue(indiranagarGarages.any { it.name.contains("Apex Auto Care") })

        // Search by garage name
        val speedy = repository.searchMechanics("Speedy Wheels")
        assertEquals(1, speedy.size)
        assertEquals("Speedy Wheels Express Hub", speedy[0].name)
    }

    @Test
    fun filterByService_returnsMatchingGarages() = runBlocking {
        // Filter by Towing
        val towingGarages = repository.filterByService("Towing")
        assertFalse(towingGarages.isEmpty())
        assertTrue(towingGarages.all { m -> m.services.any { it.contains("Towing", ignoreCase = true) } })

        // Filter by Battery Jumpstart
        val batteryGarages = repository.filterByService("Battery")
        assertFalse(batteryGarages.isEmpty())
    }

    @Test
    fun serviceRequests_createAndRetrieve_successful() = runBlocking {
        val initialCount = repository.getServiceRequests().size
        assertTrue(initialCount >= 2)

        val newRequest = ServiceRequest(
            customerName = "Vikram Malhotra",
            phoneNumber = "+91 98888 11111",
            vehicleNumber = "KA 01 ZZ 9999",
            service = "Battery Jumpstart",
            problemDescription = "Dead battery after parking",
            mechanicName = "RoadRescue Instant Towing & SOS",
            status = "Mechanic En Route",
            requestTime = "Just now"
        )

        val created = repository.createServiceRequest(newRequest)
        assertEquals("Vikram Malhotra", created.customerName)

        val updatedRequests = repository.getServiceRequests()
        assertEquals(initialCount + 1, updatedRequests.size)
        assertEquals("Vikram Malhotra", updatedRequests[0].customerName)
    }
}
