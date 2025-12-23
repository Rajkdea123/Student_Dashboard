package com.example.pw_assignment.domain.usecases

import android.content.Context
import com.example.pw_assignment.data.utils.AuthPrefs
import com.example.pw_assignment.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

/**
 * Use case for user login
 * Handles authentication logic and session persistence
 */
class LoginUseCase(
    private val authRepository: AuthRepository
) {
    /**
     * Perform login with School ID and Student ID
     * @param context Android context for SharedPreferences
     * @param schoolId School identifier
     * @param studentId Student identifier (used as password)
     * @return Result with FirebaseUser on success
     */
    suspend operator fun invoke(
        context: Context,
        schoolId: String,
        studentId: String
    ): Result<FirebaseUser> {
        // Validate inputs
        if (schoolId.isBlank() || studentId.isBlank()) {
            return Result.failure(Exception("School ID and Student ID cannot be empty"))
        }

        // Attempt authentication
        val result = authRepository.login(schoolId, studentId)

        // Save credentials on success
        result.onSuccess {
            AuthPrefs.setLoggedIn(context, true, schoolId, studentId)
        }

        return result
    }
}
