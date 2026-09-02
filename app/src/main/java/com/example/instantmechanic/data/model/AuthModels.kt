package com.example.instantmechanic.data.model

data class LoginRequest(
    val emailOrPhone: String,
    val password: String
)

data class SignUpRequest(
    val name: String,
    val email: String,
    val phone: String,
    val vehicleType: String,
    val vehicleNumber: String,
    val password: String
)

data class PasswordResetRequest(
    val emailOrPhone: String,
    val otp: String = "4829",
    val newPassword: String
)

data class UserDto(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String,
    val vehicleType: String? = "Car",
    val vehicleNumber: String? = "KA 01 MJ 4521",
    val role: String? = "CUSTOMER"
)

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val user: UserDto?,
    val token: String?
)
