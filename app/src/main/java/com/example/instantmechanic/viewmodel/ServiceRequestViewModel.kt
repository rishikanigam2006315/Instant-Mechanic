package com.example.instantmechanic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instantmechanic.data.model.ServiceRequest
import com.example.instantmechanic.data.repository.MechanicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServiceRequestViewModel : ViewModel() {

    private val repository = MechanicRepository()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _serviceRequests = MutableStateFlow<List<ServiceRequest>>(emptyList())
    val serviceRequests: StateFlow<List<ServiceRequest>> = _serviceRequests

    init {
        loadRequests()
    }

    fun loadRequests() {
        viewModelScope.launch {
            try {
                _serviceRequests.value = repository.getServiceRequests()
            } catch (e: Exception) {
                // Retain current in case of error
            }
        }
    }

    fun submitRequest(
        customerName: String,
        phoneNumber: String,
        vehicleNumber: String,
        service: String,
        problemDescription: String,
        mechanicName: String = "Apex Auto Care"
    ) {
        viewModelScope.launch {
            _isSubmitting.value = true
            _error.value = null
            _successMessage.value = null

            try {
                val request = ServiceRequest(
                    customerName = customerName,
                    phoneNumber = phoneNumber,
                    vehicleNumber = vehicleNumber,
                    service = service,
                    problemDescription = problemDescription,
                    mechanicName = mechanicName,
                    status = "Mechanic En Route",
                    requestTime = "Today, Just now"
                )

                repository.createServiceRequest(request)
                loadRequests()

                _successMessage.value =
                    "Service request submitted successfully! Your mechanic has been notified."
            } catch (e: Exception) {
                _error.value =
                    e.message ?: "Failed to submit service request"
            } finally {
                _isSubmitting.value = false
            }
        }
    }

    fun clearSuccessMessage() {
        _successMessage.value = null
    }
}