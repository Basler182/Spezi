package eu.schk.chart.repository

import java.time.LocalDateTime

data class HealthInfoModel(
    val iconDrawable: Int,
    val healthType: String,
    val valueReadings: List<Pair<LocalDateTime, Int>>,
    val averageValue: Int,
    val maxValue: Int,
    val unit: String
)