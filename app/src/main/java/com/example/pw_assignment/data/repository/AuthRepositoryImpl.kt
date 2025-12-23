package com.example.pw_assignment.data.repository

import com.example.pw_assignment.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

/**
 * Implementation of AuthRepository using Firebase Authentication
 */
class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    /**
     * Maps School ID to email format for Firebase authentication
     * Example: SGGP782001 -> SGGP782001@gmail.com
     */
    private fun mapSchoolIdToEmail(schoolId: String): String {
        return "${schoolId}@gmail.com"
    }

    override suspend fun login(schoolId: String, studentId: String): Result<FirebaseUser> {
        return try {
            val email = mapSchoolIdToEmail(schoolId)
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, studentId).await()
            
            authResult.user?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Authentication failed: User is null"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun isAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
