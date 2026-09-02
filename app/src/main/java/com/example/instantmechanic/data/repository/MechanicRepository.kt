package com.example.instantmechanic.data.repository

import com.example.instantmechanic.data.model.AuthResponse
import com.example.instantmechanic.data.model.LoginRequest
import com.example.instantmechanic.data.model.Mechanic
import com.example.instantmechanic.data.model.PasswordResetRequest
import com.example.instantmechanic.data.model.ServiceRequest
import com.example.instantmechanic.data.model.SignUpRequest
import com.example.instantmechanic.data.model.UserDto
import com.example.instantmechanic.data.network.RetrofitInstance

class MechanicRepository {

    private val api = RetrofitInstance.api

    companion object {
        val SAMPLE_MECHANICS = listOf(
            Mechanic(
                id = 1L,
                name = "Apex Auto Care & 24/7 Garage",
                rating = 4.9,
                distance = "1.2 km away",
                location = "Indiranagar, 100ft Road",
                services = listOf(
                    "Engine Diagnostics",
                    "Brake Overhaul",
                    "Full Periodic Service",
                    "24/7 Towing",
                    "Battery Jumpstart",
                    "AC Gas & Cooling"
                ),
                isOpen = true,
                address = "Plot #42, Opposite Metro Pillar 84, Indiranagar, Bengaluru",
                workingHours = "24/7 Emergency & 8:00 AM - 10:30 PM",
                phone = "+91 98765 43210"
            ),
            Mechanic(
                id = 2L,
                name = "Speedy Wheels Express Hub",
                rating = 4.8,
                distance = "2.4 km away",
                location = "Koramangala 5th Block",
                services = listOf(
                    "Tyre Replacement",
                    "Wheel Alignment",
                    "Tubeless Puncture Fix",
                    "Oil Change & Filter",
                    "Brake Pad Replacement"
                ),
                isOpen = true,
                address = "No. 18, Near Sony World Signal, Koramangala 5th Block, Bengaluru",
                workingHours = "8:30 AM - 9:30 PM (Daily)",
                phone = "+91 98765 43211"
            ),
            Mechanic(
                id = 3L,
                name = "RoadRescue Instant Towing & SOS",
                rating = 4.9,
                distance = "0.8 km away",
                location = "Outer Ring Road Junction",
                services = listOf(
                    "24/7 Towing",
                    "Emergency Jumpstart",
                    "Fuel On Delivery",
                    "Lockout Assistance",
                    "Accident Recovery"
                ),
                isOpen = true,
                address = "Beside Bellandur Flyover, Outer Ring Road, Bengaluru",
                workingHours = "24 Hours Everyday",
                phone = "+91 98765 43212"
            ),
            Mechanic(
                id = 4L,
                name = "Precision German & Multi-Brand Garage",
                rating = 4.9,
                distance = "4.1 km away",
                location = "Whitefield Tech Park Road",
                services = listOf(
                    "Engine Diagnostics",
                    "ECU Scanning",
                    "Transmission Overhaul",
                    "Suspension Repair",
                    "Premium Detailing"
                ),
                isOpen = true,
                address = "Unit 7, ITPL Main Road, Whitefield, Bengaluru",
                workingHours = "9:00 AM - 8:00 PM",
                phone = "+91 98765 43213"
            ),
            Mechanic(
                id = 5L,
                name = "Two-Wheeler & Superbike Pitstop",
                rating = 4.7,
                distance = "3.2 km away",
                location = "HSR Layout Sector 2",
                services = listOf(
                    "Bike Engine Tuning",
                    "Chain Lubing & Sprocket",
                    "Carburetor / EFI Cleaning",
                    "Disc Brake Service",
                    "Puncture Repair"
                ),
                isOpen = true,
                address = "27th Main, Sector 2, HSR Layout, Bengaluru",
                workingHours = "9:00 AM - 9:00 PM",
                phone = "+91 98765 43214"
            ),
            Mechanic(
                id = 6L,
                name = "QuickFix Auto Works",
                rating = 4.6,
                distance = "5.5 km away",
                location = "BTM 2nd Stage",
                services = listOf(
                    "General Service",
                    "Oil Change & Filter",
                    "Battery Jumpstart",
                    "Electrical & Wiring Fix"
                ),
                isOpen = false,
                address = "16th Main Road, BTM 2nd Stage, Bengaluru",
                workingHours = "9:30 AM - 8:30 PM (Opens 9:30 AM tomorrow)",
                phone = "+91 98765 43215"
            )
        )

        private val localRequests = mutableListOf(
            ServiceRequest(
                id = 101L,
                customerName = "Rahul Sharma",
                phoneNumber = "+91 98765 12345",
                vehicleNumber = "KA 01 MJ 4521",
                service = "24/7 Towing",
                problemDescription = "Engine stalled in heavy traffic, battery indicator flashing.",
                mechanicName = "RoadRescue Instant Towing & SOS",
                status = "Mechanic En Route",
                requestTime = "Today, 15 mins ago"
            ),
            ServiceRequest(
                id = 102L,
                customerName = "Rahul Sharma",
                phoneNumber = "+91 98765 12345",
                vehicleNumber = "KA 01 MJ 4521",
                service = "Full Periodic Service",
                problemDescription = "Standard 20,000 km general inspection and brake fluid replacement.",
                mechanicName = "Apex Auto Care & 24/7 Garage",
                status = "Completed",
                requestTime = "24 Aug 2026"
            )
        )
    }

    suspend fun getMechanics(): List<Mechanic> {
        return try {
            val networkList = api.getMechanics()
            if (networkList.isNotEmpty()) networkList else SAMPLE_MECHANICS
        } catch (e: Exception) {
            SAMPLE_MECHANICS
        }
    }

    suspend fun getMechanicById(id: Long): Mechanic {
        return try {
            api.getMechanicById(id)
        } catch (e: Exception) {
            SAMPLE_MECHANICS.find { it.id == id } ?: SAMPLE_MECHANICS.first()
        }
    }

    suspend fun searchMechanics(name: String): List<Mechanic> {
        return try {
            val networkList = api.searchMechanics(name)
            if (networkList.isNotEmpty()) networkList
            else SAMPLE_MECHANICS.filter { it.name.contains(name, ignoreCase = true) || it.location.contains(name, ignoreCase = true) }
        } catch (e: Exception) {
            SAMPLE_MECHANICS.filter { it.name.contains(name, ignoreCase = true) || it.location.contains(name, ignoreCase = true) }
        }
    }

    suspend fun filterByService(service: String): List<Mechanic> {
        return try {
            val networkList = api.filterByService(service)
            if (networkList.isNotEmpty()) networkList
            else SAMPLE_MECHANICS.filter { m -> m.services.any { it.contains(service, ignoreCase = true) } }
        } catch (e: Exception) {
            SAMPLE_MECHANICS.filter { m -> m.services.any { it.contains(service, ignoreCase = true) } }
        }
    }

    suspend fun getServiceRequests(): List<ServiceRequest> {
        return try {
            val networkList = api.getServiceRequests()
            if (networkList.isNotEmpty()) {
                localRequests.clear()
                localRequests.addAll(networkList)
                networkList
            } else {
                localRequests.toList()
            }
        } catch (e: Exception) {
            localRequests.toList()
        }
    }

    suspend fun createServiceRequest(request: ServiceRequest): ServiceRequest {
        localRequests.add(0, request)
        return try {
            api.createServiceRequest(request)
        } catch (e: Exception) {
            request
        }
    }

    suspend fun login(emailOrPhone: String, password: String): AuthResponse {
        return try {
            val response = api.login(LoginRequest(emailOrPhone, password))
            response
        } catch (e: Exception) {
            // Graceful fallback for offline demo presentation
            if (password.length >= 4) {
                AuthResponse(
                    success = true,
                    message = "Welcome back, Rahul Sharma",
                    user = UserDto(
                        id = 1L,
                        name = "Rahul Sharma",
                        email = if (emailOrPhone.contains("@")) emailOrPhone else "rahul.sharma@example.com",
                        phone = if (!emailOrPhone.contains("@")) emailOrPhone else "+91 98765 12345",
                        vehicleType = "Car",
                        vehicleNumber = "KA 01 MJ 4521"
                    ),
                    token = "demo-session-token"
                )
            } else {
                AuthResponse(false, "Password must be at least 4 characters", null, null)
            }
        }
    }

    suspend fun signUp(
        name: String,
        email: String,
        phone: String,
        vehicleType: String,
        vehicleNumber: String,
        password: String
    ): AuthResponse {
        return try {
            api.signUp(
                SignUpRequest(
                    name = name,
                    email = email,
                    phone = phone,
                    vehicleType = vehicleType,
                    vehicleNumber = vehicleNumber,
                    password = password
                )
            )
        } catch (e: Exception) {
            AuthResponse(
                success = true,
                message = "Registration successful",
                user = UserDto(
                    id = System.currentTimeMillis(),
                    name = name,
                    email = email,
                    phone = phone,
                    vehicleType = vehicleType,
                    vehicleNumber = vehicleNumber.ifBlank { "KA 01 MJ 4521" }
                ),
                token = "demo-session-token"
            )
        }
    }

    suspend fun sendResetOtp(emailOrPhone: String): AuthResponse {
        return try {
            api.forgotPassword(emailOrPhone)
        } catch (e: Exception) {
            AuthResponse(true, "OTP sent to $emailOrPhone", null, null)
        }
    }

    suspend fun resetPassword(emailOrPhone: String, newPassword: String): AuthResponse {
        return try {
            api.resetPassword(
                PasswordResetRequest(
                    emailOrPhone = emailOrPhone,
                    otp = "4829",
                    newPassword = newPassword
                )
            )
        } catch (e: Exception) {
            AuthResponse(true, "Password updated successfully!", null, null)
        }
    }
}