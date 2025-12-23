package com.example.pw_assignment.data.api

import com.example.pw_assignment.data.model.StudentDto
import retrofit2.http.GET

interface StudentApi {
    @GET("student_dashboard.json?alt=media&token=0091b4c2-2ee2-4326-99cd-96d5312b34bd")
    suspend fun getStudentDashboard(): StudentDto
}