package eu.schk.chart.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.schk.chart.R
import eu.schk.chart.repository.DefaultHealthInfoRepository
import eu.schk.chart.repository.HealthInfoModel
import eu.schk.designsystem.SpeziTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun HealthInfoWidget(healthInfoModel: HealthInfoModel, modifier: Modifier = Modifier) {
    val maxRate = healthInfoModel.valueReadings.maxByOrNull { it.second }?.second ?: 140
    val minRate = healthInfoModel.valueReadings.minByOrNull { it.second }?.second ?: 60

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ), modifier = modifier
            .fillMaxWidth(0.5f)
            .height(200.dp)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(id = healthInfoModel.iconDrawable),
                    contentDescription = "Health Info",
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(4.dp))
                val rotationDegree = if (healthInfoModel.valueReadings.isNotEmpty()) when {
                    healthInfoModel.valueReadings.last().second > healthInfoModel.valueReadings.first().second -> 180f
                    healthInfoModel.valueReadings.last().second < healthInfoModel.valueReadings.first().second -> 270f
                    else -> 225f
                } else {
                    0f
                }
                Icon(
                    painterResource(id = R.drawable.ic_arrow_bottom_left),
                    contentDescription = "Heart Rate",
                    tint = Color.Red,
                    modifier = Modifier.rotate(rotationDegree)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    healthInfoModel.healthType,
                    color = Color.Gray,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (healthInfoModel.valueReadings.size < 2) {
                Text(
                    "No data available",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Red
                )
            } else {
                Canvas(
                    contentDescription = "Health Info Chart",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(200.dp)
                ) {
                    drawLineGraph(
                        healthInfoModel.valueReadings, maxRate, minRate, healthInfoModel.unit
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun DrawScope.drawLineGraph(
    readings: List<Pair<LocalDateTime, Int>>, maxRate: Int, minRate: Int, unit: String
) {
    if (readings.size < 2) {
        return
    }
    val dateFormatter = DateTimeFormatter.ofPattern("EE")

    // Draw x-axis
    drawLine(
        color = Color.Black, start = Offset(0f, size.height), end = Offset(size.width, size.height)
    )

    // Draw y-axis
    drawLine(
        color = Color.Black, start = Offset(0f, 0f), end = Offset(0f, size.height)
    )

    // Draw y-axis scale
    val scaleLines = 4 // Number of scale lines to draw on y-axis
    for (i in 0 until scaleLines) {
        val y = size.height * (i.toFloat() / (scaleLines - 1))
        drawLine(
            color = Color.Gray,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = 1.dp.toPx()
        )
        drawContext.canvas.nativeCanvas.drawText(String.format(
            "%.0f", maxRate - (maxRate - minRate) * (i.toFloat() / (scaleLines - 1))
        ) + " $unit", -45f, y, android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 30f
        })
    }

    // Draw average heart rate line
    val averageRate = readings.sumOf { it.second } / readings.size
    val averageY = size.height * (1 - (averageRate - minRate).toFloat() / (maxRate - minRate))
    drawLine(
        color = Color.Blue,
        start = Offset(0f, averageY),
        end = Offset(size.width, averageY),
        strokeWidth = 2.dp.toPx(),
        cap = StrokeCap.Round
    )

    // Draw average value label
    drawContext.canvas.nativeCanvas.drawText("Avg",
        size.width - 50f,
        averageY - 10f,
        android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 30f
        })

    for (i in 0 until readings.size - 1) {
        val p1 = readings[i]
        val p2 = readings[i + 1]

        val fromPercentage = (p1.second - minRate).toFloat() / (maxRate - minRate)
        val toPercentage = (p2.second - minRate).toFloat() / (maxRate - minRate)

        val fromPoint = Offset(
            x = size.width * (i.toFloat() / (readings.size - 1)),
            y = size.height * (1 - fromPercentage)
        )
        val toPoint = Offset(
            x = size.width * ((i + 1).toFloat() / (readings.size - 1)),
            y = size.height * (1 - toPercentage)
        )

        // Draw line
        drawLine(
            color = Color.Red,
            start = fromPoint,
            end = toPoint,
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Draw x-axis labels
        drawContext.canvas.nativeCanvas.drawText(p1.first.format(dateFormatter),
            fromPoint.x,
            size.height + 30f,
            android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 30f
            })
    }

    // Draw the label for the last reading
    val lastReading = readings.last()
    val lastPoint = Offset(
        x = size.width,
        y = size.height * (1 - (lastReading.second - minRate).toFloat() / (maxRate - minRate))
    )
    drawContext.canvas.nativeCanvas.drawText(lastReading.first.format(dateFormatter),
        lastPoint.x,
        size.height + 30f,
        android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 30f
        })
}

@Preview(showBackground = true)
@Composable
fun HealthInfoWidgetPreview() {
    SpeziTheme {
        Row(modifier = Modifier.width(700.dp)) {
            HealthInfoWidget(DefaultHealthInfoRepository().getHealthInfo(), Modifier.weight(1f))
            HealthInfoWidget(
                DefaultHealthInfoRepository().getHealthInfos()[1], Modifier.weight(1f)
            )
        }
    }
}