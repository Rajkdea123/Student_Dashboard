package com.example.pw_assignment.domain.repository

import com.example.pw_assignment.data.model.StudentDto

interface StudentRepository {
    suspend fun getStudentDashboard(): StudentDto
}