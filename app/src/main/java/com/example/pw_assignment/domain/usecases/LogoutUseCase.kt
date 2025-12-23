package com.example.pw_assignment.domain.usecases

import android.content.Context
import com.example.pw_assignment.data.utils.AuthPrefs
import com.example.pw_assignment.domain.repository.AuthRepository

/**
 * Use case for user logout
 * Handles sign out and clearing local session data
 */
class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    /**
     * Perform logout
     * @param context Android context for SharedPreferences
     * @return Result indicating success or failure
     */
    suspend operator fun invoke(context: Context): Result<Unit> {
        // Sign out from Firebase
        val result = authRepository.logout()

        // Clear local session data regardless of Firebase result
        // This ensures user is logged out locally even if Firebase call fails
        AuthPrefs.logout(context)

        return result
    }
}
