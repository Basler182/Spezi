package eu.schk.chart.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.schk.designsystem.CardinalRedLight
import eu.schk.designsystem.CoolGrey
import eu.schk.designsystem.RectangleBlue

@Composable
fun HealthInfoBox(
    title: String,
    lastValueDate: String,
    value: Int,
    maxValue: Int,
    unit: String,
    icon: ImageVector,
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(RectangleBlue),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$value $unit",
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = lastValueDate,
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CircleChartIcon(icon, value.toFloat() / maxValue.toFloat())
        }
    }
}

@Composable
fun CircleChartIcon(icon: ImageVector, percentage: Float) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(64.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweepAngle = percentage * 360f
            drawArc(
                color = CoolGrey,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 4.dp.toPx())
            )
            drawArc(
                color = CardinalRedLight,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 4.dp.toPx())
            )
        }
        Icon(icon, contentDescription = null, modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun HealthInfoBoxPreview() {
    HealthInfoBox(
        title = "Heart",
        lastValueDate = "Last updated 2 days ago",
        value = 72,
        maxValue = 90,
        unit = "bpm",
        icon = Icons.Default.CheckCircle
    )
}