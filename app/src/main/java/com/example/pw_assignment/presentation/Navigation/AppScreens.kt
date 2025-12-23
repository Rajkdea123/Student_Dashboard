package com.example.pw_assignment.presentation.Navigation

import kotlinx.serialization.Serializable

sealed interface AppScreens {

 val route: String

 data object SignIn : AppScreens {
  override val route = "sign_in"
 }

 data object Home : AppScreens {
  override val route = "home"
 }

 data object Notification : AppScreens {
  override val route = "notification"
 }

}