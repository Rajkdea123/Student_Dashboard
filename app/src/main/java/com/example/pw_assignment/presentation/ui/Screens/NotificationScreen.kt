package com.example.pw_assignment.presentation.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pw_assignment.R
import com.example.pw_assignment.presentation.theme.PW_AssignmentTheme
import com.example.pw_assignment.presentation.theme.redditSansFontFamily
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    onBack: () -> Unit,
    onLogoutClick: ()->Unit

) {
    val headingColor = Color(0xFF1B2124)
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true // makes status bar icons black
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Notifications & Settings",
                        color = headingColor,
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = TextUnit(0.1f, TextUnitType.Sp),
                        fontFamily = redditSansFontFamily,
                        fontSize = 21.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text(
                text = "Notifications",
                fontWeight = FontWeight.Bold,
                color = headingColor,
                fontSize = 18.sp,
                letterSpacing = TextUnit(0.1f, TextUnitType.Sp),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            NotificationItem(
                title = "Missed quiz in physics in yesterday",
                timeAgo = "2 hours ago",
                indicatorColor = Color(0xFFFFB370).copy(alpha = 0.8f),
                background = Color(0xFFFFF9F4)
            )

            Spacer(Modifier.height(15.dp))

            NotificationItem(
                title = "Badge earned",
                timeAgo = "8 hours ago",
                indicatorColor = Color(0xFF996EB5),
                background = Color(0xBCF2E6FF)
            )

            Spacer(Modifier.height(15.dp))

            NotificationItem(
                title = "Teacher Note",
                timeAgo = "1 day ago",
                indicatorColor = Color(0xFF22C55D),
                background = Color(0xFFF2FFF4)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Settings",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = TextUnit(0.1f, TextUnitType.Sp),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            SettingsItem(
                painter = painterResource(R.drawable.child),
                title = "Switch Child",
                subtitle = "Change active child profile"
            )

            SettingsItem(
                painter = painterResource(R.drawable.lang),
                title = "Language",
                subtitle = "English"
            )

            SettingsItem(
                painter = painterResource(R.drawable.logout),
                title = "Logout",
                subtitle = "Sign out of your account",
                iconTint = Color(0xFFE57373),
                onClick = { onLogoutClick() }
            )

        }
    }
}

@Composable
fun NotificationItem(
    title: String,
    timeAgo: String,
    indicatorColor: Color,
    background: Color
) {
    val headingColor = Color(0xFF1B2124)
    val contentColor = Color(0xFF757575)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(background, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(indicatorColor, RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier) {
            Text(
                text = title, fontWeight = FontWeight.SemiBold,
                letterSpacing = TextUnit(0.1f, TextUnitType.Sp),
                color = headingColor,
            )
            Text(
                text = timeAgo, fontSize = 12.sp,
                letterSpacing = TextUnit(0.1f, TextUnitType.Sp),
                color = contentColor
            )
        }
    }
}

@Composable
fun SettingsItem(
    painter: Painter,
    title: String,
    subtitle: String,
    iconTint: Color = Color.Black,
    onClick: (() -> Unit)? = null
) {
    val headingColor = Color(0xFF1B2124)
    val contentColor = Color(0xFF757575)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() }, // handle click only if lambda is provided
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = title,
            tint = iconTint,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                title,
                fontWeight = FontWeight.SemiBold,
                color = headingColor,
                letterSpacing = TextUnit(0.1f, TextUnitType.Sp)
            )
            Text(
                subtitle,
                color = contentColor,
                fontSize = 12.sp,
                letterSpacing = TextUnit(0.1f, TextUnitType.Sp)
            )
        }
    }
}
