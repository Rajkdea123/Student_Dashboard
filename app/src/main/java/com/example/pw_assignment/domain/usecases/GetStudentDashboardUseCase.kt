package com.example.pw_assignment.domain.usecases

import com.example.pw_assignment.domain.repository.StudentRepository

class GetStudentDashboardUseCase(
    private val repository: StudentRepository
) {
    suspend operator fun invoke() = repository.getStudentDashboard()
}