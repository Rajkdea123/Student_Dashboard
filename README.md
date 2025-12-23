# 📱 PW Assignment – Android App (Jetpack Compose)

An Android application built using **Jetpack Compose**, following **Clean Architecture** principles with **MVVM**, **Koin** for dependency injection, and **Navigation Compose**.

---


https://github.com/Rajkdea123/Student_Dashboard/issues/1#issue-3758012405
## 🚀 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** Clean Architecture + MVVM
- **Dependency Injection:** Koin
- **Navigation:** Navigation Compose
- **State Management:** ViewModel
- **Design:** Material 3

---

## 🧱 Architecture Overview

The project follows **Clean Architecture**, separating responsibilities into clear layers:

- **Data layer** → Handles APIs, data sources, and repository implementations
- **Domain layer** → Contains business logic and use cases
- **Presentation layer** → UI, ViewModels, and navigation
- **DI layer** → Dependency injection setup

This improves:
- ✅ Testability  
- ✅ Maintainability  
- ✅ Scalability  

---

## 📁 Project Structure

app/
├── data/
│ ├── model/ # Data models & DTOs
│ ├── remote/ # API services & network layer
│ ├── repository/ # Repository implementations
│ └── utils/ # Utility classes (AuthPrefs, etc.)
│
├── domain/
│ ├── repository/ # Repository interfaces
│ └── usecases/ # Business logic use cases
│
├── presentation/
│ ├── Navigation/ # Navigation graph (NavHost, routes)
│ ├── theme/ # App theme (Color, Typography, Theme)
│ ├── ui/
│ │ └── Screens/ # Jetpack Compose screens
│ ├── viewmodels/ # ViewModels
│ └── MainActivity.kt # App entry point (Compose host)
│
└── di/
└── AppModule.kt # Dependency Injection modules



## 🔁 App Flow

1. **MainActivity**
   - Hosts Compose content
   - Initializes `NavController`

2. **Navigation**
   - Handles screen routing via `NavHost`

3. **Screens**
   - Stateless UI components
   - Observe state from ViewModels

4. **ViewModels**
   - Hold UI state
   - Call UseCases

5. **UseCases**
   - Contain business logic
   - Interact with repository interfaces

6. **Repositories**
   - Fetch data from remote/local sources

---

## 💉 Dependency Injection (Koin)

Koin is initialized in the `Application` class.

```kotlin
startKoin {
    androidContext(this@PWApplication)
    modules(appModule)
}


## ▶️ How to Run the Project

1. Clone the repository  
2. Open in Android Studio  
3. Sync Gradle  
4. Run on an emulator or physical device  

### 🔐 Login Setup (Firebase)

- The app accepts a **School ID** for login  
- School ID is internally mapped to an email format
School ID  →  test
Student ID → test@1234


- Make sure the following user exists in **Firebase Authentication**:
  - Email: `test@gmail.com`
  - Password: `test@1234`

