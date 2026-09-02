package com.example.instantmechanic.data.model

data class ServiceRequest(
    val customerName: String,
    val phoneNumber: String,
    val vehicleNumber: String,
    val service: String,
    val problemDescription: String,
    val id: Long = System.currentTimeMillis(),
    val mechanicName: String = "Apex Auto Care",
    val status: String = "Assigned",
    val requestTime: String = "Today, Just now"
)