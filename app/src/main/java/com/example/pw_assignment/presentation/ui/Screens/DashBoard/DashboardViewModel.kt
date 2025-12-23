package com.example.pw_assignment.presentation.ui.Screens.DashBoard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pw_assignment.data.model.StudentDto
import com.example.pw_assignment.domain.usecases.GetStudentDashboardUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DashboardState(
    val isLoading: Boolean = false,
    val student: StudentDto? = null,
    val error: String? = null
)

class DashboardViewModel(
    private val getStudentDashboardUseCase: GetStudentDashboardUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState(isLoading = true))
    val state: StateFlow<DashboardState> = _state

    init {
        fetchDashboard()
    }

    private fun fetchDashboard() {
        viewModelScope.launch {
            try {
                _state.value = DashboardState(isLoading = true)
                val result = getStudentDashboardUseCase()
                _state.value = DashboardState(student = result)
            } catch (e: Exception) {
                _state.value = DashboardState(error = e.localizedMessage ?: "Unknown Error")
            }
        }
    }
}