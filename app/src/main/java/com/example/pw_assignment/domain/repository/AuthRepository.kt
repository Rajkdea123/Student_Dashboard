package com.example.pw_assignment.domain.repository

import com.google.firebase.auth.FirebaseUser

/**
 * Repository interface for authentication operations
 */
interface AuthRepository {
    /**
     * Login with school ID and student ID
     * @param schoolId School identifier (will be mapped to email)
     * @param studentId Student identifier (used as password)
     * @return Result with FirebaseUser on success, exception on failure
     */
    suspend fun login(schoolId: String, studentId: String): Result<FirebaseUser>
    
    /**
     * Logout current user
     */
    suspend fun logout(): Result<Unit>
    
    /**
     * Get currently authenticated user
     * @return FirebaseUser if authenticated, null otherwise
     */
    fun getCurrentUser(): FirebaseUser?
    
    /**
     * Check if user is currently authenticated
     */
    fun isAuthenticated(): Boolean
}
