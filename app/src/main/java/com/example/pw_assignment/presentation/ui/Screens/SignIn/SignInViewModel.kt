package com.example.pw_assignment.presentation.ui.Screens.SignIn

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pw_assignment.domain.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * UI State for Sign In screen
 */
data class SignInUiState(
    val schoolId: String = "",
    val studentId: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val isAuthenticated: Boolean = false
)

/**
 * ViewModel for Sign In screen with Firebase Authentication
 */
class SignInViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    /**
     * Update School ID input
     */
    fun onSchoolIdChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            schoolId = value,
            error = "" // Clear error on input change
        )
    }

    /**
     * Update Student ID input
     */
    fun onStudentIdChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            studentId = value,
            error = "" // Clear error on input change
        )
    }

    /**
     * Perform sign in with Firebase Authentication
     */
    fun signIn(context: Context) {
        val current = _uiState.value

        // Validate inputs
        if (current.schoolId.isBlank() || current.studentId.isBlank()) {
            _uiState.value = current.copy(error = "Please fill all fields")
            return
        }

        // Start loading
        _uiState.value = current.copy(isLoading = true, error = "")

        viewModelScope.launch {
            val result = loginUseCase(context, current.schoolId, current.studentId)

            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    error = ""
                )
            }.onFailure { exception ->
                val errorMessage = when {
                    exception.message?.contains("password is invalid") == true -> 
                        "Invalid Student ID"
                    exception.message?.contains("no user record") == true -> 
                        "Invalid School ID"
                    exception.message?.contains("network") == true -> 
                        "Network error. Please check your connection"
                    else -> 
                        "Invalid School ID or Student ID"
                }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isAuthenticated = false,
                    error = errorMessage
                )
            }
        }
    }

    /**
     * Reset authentication state (used after navigation)
     */
    fun resetAuthState() {
        _uiState.value = _uiState.value.copy(isAuthenticated = false)
    }
}

