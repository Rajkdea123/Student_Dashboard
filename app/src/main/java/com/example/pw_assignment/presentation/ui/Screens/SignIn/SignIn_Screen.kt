package com.example.pw_assignment.presentation.ui.Screens.SignIn

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pw_assignment.R
import com.example.pw_assignment.data.utils.AuthPrefs
import com.example.pw_assignment.presentation.Navigation.AppScreens
import com.example.pw_assignment.presentation.theme.redditSansFontFamily
import com.example.pw_assignment.presentation.util.BottomWaveCutoutShape
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignIn_Screen(
    navController: NavController,
    viewModel: SignInViewModel = koinViewModel(),
    context: Context = LocalContext.current,
    onSignButtonInClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Check login state — skip screen if already logged in
    LaunchedEffect(Unit) {
        if (AuthPrefs.isLoggedIn(context)) {
            navController.navigate(AppScreens.Home.route) {
                popUpTo(AppScreens.SignIn.route) { inclusive = true }
            }
        }
    }

    // Handle successful authentication
    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) {
            viewModel.resetAuthState()
            onSignButtonInClick()
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Column(modifier = Modifier.fillMaxSize()) {
            // ------------------ TOP SECTION ------------------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.signin_bg_img),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )
                Image(
                    painter = painterResource(R.drawable.character_img),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = screenHeight * 0.15f)
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.01f))

            Text(
                text = "Welcome to",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White,
                fontSize = (screenWidth.value * 0.08f).sp,
                fontFamily = redditSansFontFamily,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.005f))

            Text(
                text = "Quizzy!",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White,
                fontSize = (screenWidth.value * 0.08f).sp,
                fontFamily = redditSansFontFamily,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.015f))

            // ------------------ BOTTOM SECTION ------------------
            Box(modifier = Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    shape = BottomWaveCutoutShape(120f, 600f, 150f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth()
                                .padding(
                                    top = screenHeight * 0.02f,
                                    bottom = screenHeight * 0.12f
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Let's Get you Signed in",
                                color = Color.Black,
                                fontSize = (screenWidth.value * 0.035f).sp,
                                fontFamily = redditSansFontFamily,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(screenHeight * 0.03f))

                            CustomTextField(
                                value = uiState.schoolId,
                                onValueChange = viewModel::onSchoolIdChanged,
                                placeholder = "School ID",
                                modifier = Modifier.fillMaxWidth(0.85f),
                                enabled = !uiState.isLoading
                            )

                            Spacer(modifier = Modifier.height(screenHeight * 0.02f))

                            CustomTextField(
                                value = uiState.studentId,
                                onValueChange = viewModel::onStudentIdChanged,
                                placeholder = "Student ID",
                                modifier = Modifier.fillMaxWidth(0.85f),
                                enabled = !uiState.isLoading
                            )

                            if (uiState.error.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = uiState.error,
                                    color = Color.Red,
                                    fontSize = 14.sp,
                                    fontFamily = redditSansFontFamily,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            if (uiState.isLoading) {
                                Spacer(modifier = Modifier.height(16.dp))
                                CircularProgressIndicator(
                                    color = Color(0xFF6C63FF),
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }

                // ------------------ SIGN-IN BUTTON ------------------
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = screenHeight * 0.025f)
                        .clickable(enabled = !uiState.isLoading) {
                            viewModel.signIn(context)
                        }
                ) {
                    Text(
                        text = if (uiState.isLoading) "Signing in..." else "Sign in",
                        color = Color.White,
                        fontFamily = redditSansFontFamily,
                        fontSize = (screenWidth.value * 0.04f).sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .background(
                color = if (enabled) Color(0xFFF5F5F5) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = if (enabled) Color.Black else Color.Gray
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )
    }
}
