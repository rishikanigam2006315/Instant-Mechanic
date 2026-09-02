package com.example.instantmechanic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instantmechanic.data.repository.MechanicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserSession(
    val name: String,
    val email: String,
    val phone: String,
    val vehicleType: String = "Car",
    val vehicleNumber: String = "KA 01 MJ 4521",
    val avatarUri: String? = null
)

class AuthViewModel : ViewModel() {

    private val repository = MechanicRepository()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _currentUser = MutableStateFlow<UserSession?>(null)
    val currentUser: StateFlow<UserSession?> = _currentUser

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    private val _authSuccess = MutableStateFlow<String?>(null)
    val authSuccess: StateFlow<String?> = _authSuccess

    fun login(emailOrPhone: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null

            if (emailOrPhone.isBlank() || password.isBlank()) {
                _authError.value = "Please enter both credentials"
                _isLoading.value = false
                return@launch
            }

            if (password.length < 4) {
                _authError.value = "Password must be at least 4 characters"
                _isLoading.value = false
                return@launch
            }

            val response = repository.login(emailOrPhone.trim(), password.trim())
            _isLoading.value = false

            if (response.success) {
                val userDto = response.user
                val user = UserSession(
                    name = userDto?.name ?: "Rahul Sharma",
                    email = userDto?.email ?: (if (emailOrPhone.contains("@")) emailOrPhone else "rahul.sharma@example.com"),
                    phone = userDto?.phone ?: (if (!emailOrPhone.contains("@")) emailOrPhone else "+91 98765 12345"),
                    vehicleType = userDto?.vehicleType ?: "Car",
                    vehicleNumber = userDto?.vehicleNumber ?: "KA 01 MJ 4521"
                )
                _currentUser.value = user
                _isLoggedIn.value = true
                onSuccess()
            } else {
                _authError.value = response.message
            }
        }
    }

    fun signUp(
        name: String,
        email: String,
        phone: String,
        vehicleType: String,
        vehicleNumber: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null

            val response = repository.signUp(
                name = name.trim(),
                email = email.trim(),
                phone = phone.trim(),
                vehicleType = vehicleType,
                vehicleNumber = vehicleNumber.ifBlank { "KA 01 MJ 4521" },
                password = password
            )
            _isLoading.value = false

            if (response.success) {
                val userDto = response.user
                val user = UserSession(
                    name = userDto?.name ?: name,
                    email = userDto?.email ?: email,
                    phone = userDto?.phone ?: phone,
                    vehicleType = userDto?.vehicleType ?: vehicleType,
                    vehicleNumber = userDto?.vehicleNumber ?: vehicleNumber.ifBlank { "KA 01 MJ 4521" }
                )
                _currentUser.value = user
                _isLoggedIn.value = true
                onSuccess()
            } else {
                _authError.value = response.message
            }
        }
    }

    fun resetPassword(emailOrPhone: String, onResetSent: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null

            val response = repository.sendResetOtp(emailOrPhone.trim())
            _isLoading.value = false

            if (response.success) {
                _authSuccess.value = response.message
                onResetSent()
            } else {
                _authError.value = response.message
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        _isLoggedIn.value = false
        _currentUser.value = null
        _authError.value = null
        _authSuccess.value = null
        onLoggedOut()
    }

    fun updateAvatarUri(uri: String?) {
        _currentUser.value = _currentUser.value?.copy(avatarUri = uri)
    }

    fun updateVehicle(vehicleType: String, vehicleNumber: String) {
        _currentUser.value = _currentUser.value?.copy(
            vehicleType = vehicleType,
            vehicleNumber = vehicleNumber
        )
    }

    fun clearErrors() {
        _authError.value = null
        _authSuccess.value = null
    }
}
