package com.example.instantmechanic.data.model

data class Mechanic(
    val id: Long,
    val name: String,
    val rating: Double,
    val distance: String,
    val location: String,
    val services: List<String>,
    val isOpen: Boolean,
    val address: String,
    val workingHours: String,
    val phone: String
)