package com.example.instantmechanic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instantmechanic.data.model.Mechanic
import com.example.instantmechanic.data.repository.MechanicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MechanicViewModel : ViewModel() {

    private val repository = MechanicRepository()

    private val _mechanics = MutableStateFlow<List<Mechanic>>(emptyList())
    val mechanics: StateFlow<List<Mechanic>> = _mechanics

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory

    fun getMechanics() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                _mechanics.value = repository.getMechanics()
            } catch (e: Exception) {
                _error.value = e.message ?: "Something went wrong"
                _mechanics.value = MechanicRepository.SAMPLE_MECHANICS
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterMechanics()
    }

    fun onCategorySelect(category: String) {
        _selectedCategory.value = category
        filterMechanics()
    }

    private fun filterMechanics() {
        viewModelScope.launch {
            val query = _searchQuery.value.trim()
            val category = _selectedCategory.value

            val baseList = repository.getMechanics()
            val filtered = baseList.filter { mechanic ->
                val matchesQuery = query.isEmpty() ||
                        mechanic.name.contains(query, ignoreCase = true) ||
                        mechanic.location.contains(query, ignoreCase = true) ||
                        mechanic.services.any { it.contains(query, ignoreCase = true) }

                val matchesCategory = category == "All" ||
                        mechanic.services.any { it.contains(category, ignoreCase = true) }

                matchesQuery && matchesCategory
            }
            _mechanics.value = filtered
        }
    }
}