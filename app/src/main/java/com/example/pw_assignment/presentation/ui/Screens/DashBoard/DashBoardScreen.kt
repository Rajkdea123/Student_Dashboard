package com.example.pw_assignment.presentation.ui.Screens.DashBoard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pw_assignment.R
import com.example.pw_assignment.data.model.PerformanceTopicDto
import com.example.pw_assignment.data.model.StudentDto
import com.example.pw_assignment.data.model.TodaySummaryDto
import com.example.pw_assignment.data.model.WeeklyOverviewDto
import com.example.pw_assignment.presentation.theme.redditSansFontFamily
import org.koin.androidx.compose.koinViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNotificationIconClick: () -> Unit,
    viewModel: DashboardViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()


    // Enable vertical scrolling
    val scrollState = rememberScrollState()

    when {
        state.isLoading -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Text(
                text = "Error: ${state.error}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        state.student != null -> {
            val data = state.student
            DashBoardContent(
                scrollState, data,
                onNotificationIconClick = onNotificationIconClick
            )
        }

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardContent(
    scrollState: ScrollState, data: StudentDto?,
    onNotificationIconClick: () -> Unit
) {
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
                    Column {
                        Text(
                            text = "Hello ${data!!.student.name}!",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontFamily = redditSansFontFamily,
                                fontSize = 24.sp
                            )
                        )
                        Text(
                            text = data.student.`class`,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 20.sp,
                                fontFamily = redditSansFontFamily,
                                color = Color.Gray
                            )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onNotificationIconClick() }) {
                        Icon(
                            painter = painterResource(R.drawable.notification_img),
                            contentDescription = "Notifications",
                            tint = Color.Black,
                            modifier = Modifier.fillMaxSize().padding(5.dp)

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
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.White)
                .padding(padding)
        ) {
            Spacer(Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
            ) {

                // 3 card below topAppBar
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // First card — text2 same as strokeColor
                    CardWithImageAndColor(
                        modifier = Modifier.weight(1f),
                        icon = painterResource(R.drawable.student),
                        strokeColor = Color(0xFFF3EDB5E),
                        text1 = "Availability",
                        text2 = data!!.student.availability.status
                    )

                    // Second card — text2 black
                    CardWithImageAndColor(
                        modifier = Modifier.weight(1f),
                        icon = painterResource(R.drawable.attempt_img),
                        strokeColor = Color(0xFFFFE9C3B),
                        text1 = "Quiz",
                        text2 = "${data.student.quiz.attempts} Attempts",
                        text2Color = Color.Black
                    )

                    // Third card — text2 black
                    CardWithImageAndColor(
                        modifier = Modifier.weight(1f),
                        icon = painterResource(R.drawable.accuracy_img),
                        strokeColor = Color(0xFFFFF4F4F),
                        text1 = "Accuracy",
                        text2 = data.student.accuracy.current,
                        text2Color = Color.Black
                    )

                }


                Spacer(modifier = Modifier.height(20.dp))


                Text(
                    text = "Today's Summary",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = redditSansFontFamily,
                        color = Color.Black,
                        fontSize = 24.sp
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                SummaryCard(
                    strokeColor = Color(0xFFF996EB5),
                    data!!.todaySummary
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Weekly OverView",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = redditSansFontFamily,
                        fontSize = 24.sp
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))
                WeeklyCard(data.weeklyOverview)
                Spacer(modifier = Modifier.height(20.dp))


            }
        }

    }
}

@Composable
fun WeeklyCard(weeklyOverview: WeeklyOverviewDto) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xff7B7F86))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Quiz Streak Section
            HeadingWithImage("Quiz Streak", R.drawable.streak_img)
            Spacer(modifier = Modifier.height(10.dp))

            val dayStatus = weeklyOverview.quizStreak.map { it.status.lowercase() == "done" }
            QuizStreak(dayStatus = dayStatus)

            Spacer(modifier = Modifier.height(20.dp))

            // Accuracy Section
            HeadingWithImage("Accuracy", R.drawable.accuracy2_img)
            Spacer(modifier = Modifier.height(10.dp))

            val accuracyLabel = weeklyOverview.overallAccuracy.label
            val accuracyPercent = weeklyOverview.overallAccuracy.percentage
            AccuracyUi(label = accuracyLabel, percent = accuracyPercent)

            Spacer(modifier = Modifier.height(20.dp))

            // Performance by Topic Section
            HeadingWithImage("Performance By Topic", R.drawable.performance_img)
            Spacer(modifier = Modifier.height(10.dp))
            PerformanceUi(performanceByTopic = weeklyOverview.performanceByTopic)

        }
    }
}


@Composable
fun PerformanceUi(performanceByTopic: List<PerformanceTopicDto>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        performanceByTopic.forEach { item ->
            PerformanceItem(
                topic = item.topic,
                trend = item.trend
            )
        }

    }
}


@Composable
fun PerformanceItem(topic: String, trend: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = topic,
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontFamily = redditSansFontFamily,
            modifier = Modifier.weight(1f)
        )

        val iconRes = if (trend.lowercase() == "up") {
            R.drawable.up_img
        } else {
            R.drawable.down_img
        }

        Image(
            painter = painterResource(id = iconRes),
            contentDescription = trend,
            modifier = Modifier.size(30.dp)
        )
    }
}


@Composable
fun AccuracyUi(label: String, percent: Int) {
    val progress = percent.coerceIn(0, 100) / 100f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontFamily = redditSansFontFamily,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFFFFE0E3))
        ) {
            // Filled portion
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFFF6776)) // Filled part color
            )
        }
        //#FF6776

    }
}


@Composable
fun QuizStreak(dayStatus: List<Boolean>) {
    val days = listOf("M", "T", "W", "T", "F", "S", "S")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEachIndexed { index, day ->
            val isCompleted = dayStatus.getOrNull(index) ?: false

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        val radius = size.minDimension / 2 - strokeWidth / 2
                        drawCircle(
                            color = Color.Gray,
                            radius = radius,
                            style = Stroke(
                                width = strokeWidth,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Image(
                        painter = painterResource(id = R.drawable.greentick_img),
                        contentDescription = "Completed",
                        modifier = Modifier.size(38.dp)
                    )
                } else {
                    Text(
                        text = day,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = redditSansFontFamily,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun HeadingWithImage(title: String, icon: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Line + Title + Image overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            // Title (positioned at start)
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontFamily = redditSansFontFamily,
                    fontSize = 21.sp
                ),
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )

            // Horizontal line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .align(Alignment.BottomStart)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Gray.copy(alpha = 0.5f),
                                Color.Black
                            )
                        )
                    )
            )

            // PNG image (overlapping line at end)
            Image(
                painter = painterResource(icon),
                contentDescription = "Quiz cards icon",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(56.dp)

            )
        }
    }
}


@Composable
fun SummaryCard(strokeColor: Color, todaySummary: TodaySummaryDto) {

    val backgroundColor = strokeColor.copy(alpha = 0.1f)
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, strokeColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(R.drawable.focused_img),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = todaySummary.mood,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = strokeColor,
                    fontFamily = redditSansFontFamily,
                    fontSize = 24.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = todaySummary.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    fontFamily = redditSansFontFamily,
                    fontSize = 17.sp
                )
            )

            IconButton(
                onClick = { /* TODO: handle click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp)) // corner radius
                    .background(Color.Black)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center // centers the icon + text together
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = todaySummary.recommendedVideo.actionText,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = redditSansFontFamily,
                        fontWeight = FontWeight.Medium
                    )
                }
            }


        }

    }

}


@Composable
fun CardWithImageAndColor(
    modifier: Modifier = Modifier,
    icon: Painter,
    strokeColor: Color,
    text1: String? = null,
    text2: String? = null,
    text2Color: Color = strokeColor
) {
    val backgroundColor = strokeColor.copy(alpha = 0.1f)

    Card(
        modifier = modifier.fillMaxSize(),
        shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, strokeColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = strokeColor,
                modifier = Modifier.size(40.dp)
            )

            text1?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    fontFamily = redditSansFontFamily,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            text2?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = text2Color,
                    fontFamily = redditSansFontFamily,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}






