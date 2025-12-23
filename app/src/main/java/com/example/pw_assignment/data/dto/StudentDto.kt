package com.example.pw_assignment.data.model

data class StudentDto(
    val student: StudentDataDto,
    val todaySummary: TodaySummaryDto,
    val weeklyOverview: WeeklyOverviewDto
)

data class StudentDataDto(
    val name: String,
    val `class`: String,
    val availability: AvailabilityDto,
    val quiz: QuizDto,
    val accuracy: AccuracyDto
)

data class AvailabilityDto(val status: String)
data class QuizDto(val attempts: Int)
data class AccuracyDto(val current: String)

data class TodaySummaryDto(
    val mood: String,
    val description: String,
    val recommendedVideo: RecommendedVideoDto,
    val characterImage: String
)

data class RecommendedVideoDto(
    val title: String,
    val actionText: String
)

data class WeeklyOverviewDto(
    val quizStreak: List<QuizStreakDto>,
    val overallAccuracy: OverallAccuracyDto,
    val performanceByTopic: List<PerformanceTopicDto>
)

data class QuizStreakDto(val day: String, val status: String)
data class OverallAccuracyDto(val percentage: Int, val label: String)
data class PerformanceTopicDto(val topic: String, val trend: String)
