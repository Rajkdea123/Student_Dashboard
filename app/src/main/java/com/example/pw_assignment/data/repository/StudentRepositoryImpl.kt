package com.example.pw_assignment.data.repository

import com.example.pw_assignment.data.api.StudentApi
import com.example.pw_assignment.data.model.StudentDto
import com.example.pw_assignment.domain.repository.StudentRepository

class StudentRepositoryImpl(
    private val api: StudentApi
) : StudentRepository {
    override suspend fun getStudentDashboard(): StudentDto {
        return api.getStudentDashboard()
    }
}