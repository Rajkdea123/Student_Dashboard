package com.example.pw_assignment.di

import com.example.pw_assignment.data.api.StudentApi
import com.example.pw_assignment.data.repository.AuthRepositoryImpl
import com.example.pw_assignment.data.repository.StudentRepositoryImpl
import com.example.pw_assignment.domain.repository.AuthRepository
import com.example.pw_assignment.domain.repository.StudentRepository
import com.example.pw_assignment.domain.usecases.GetStudentDashboardUseCase
import com.example.pw_assignment.domain.usecases.LoginUseCase
import com.example.pw_assignment.domain.usecases.LogoutUseCase
import com.example.pw_assignment.presentation.ui.Screens.DashBoard.DashboardViewModel
import com.example.pw_assignment.presentation.ui.Screens.SignIn.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // Firebase Auth
    single { FirebaseAuth.getInstance() }

    // Retrofit API
    single {
        Retrofit.Builder()
            .baseUrl("https://firebasestorage.googleapis.com/v0/b/user-contacts-ade83.appspot.com/o/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StudentApi::class.java)
    }

    // Repositories
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<StudentRepository> { StudentRepositoryImpl(get()) }

    // Use Cases
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { GetStudentDashboardUseCase(get()) }

    // ViewModels
    viewModel { SignInViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
}