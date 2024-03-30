package eu.schk.chart.repository

import eu.schk.chart.R
import java.time.LocalDateTime

interface HealthInfoRepository {
    fun getHealthInfos(): List<HealthInfoModel>
    fun getHealthInfo(): HealthInfoModel
}

class DefaultHealthInfoRepository : HealthInfoRepository {
    override fun getHealthInfo(): HealthInfoModel {
        return HealthInfoModel(
            iconDrawable = R.drawable.ic_heart,
            healthType = "Heart",
            valueReadings = listOf(
                Pair(LocalDateTime.now().minusDays(7), 70),
                Pair(LocalDateTime.now().minusDays(6), 72),
                Pair(LocalDateTime.now().minusDays(5), 90),
                Pair(LocalDateTime.now().minusDays(4), 75),
                Pair(LocalDateTime.now().minusDays(3), 80),
                Pair(LocalDateTime.now().minusDays(2), 85),
                Pair(LocalDateTime.now().minusDays(1), 78),
                Pair(LocalDateTime.now(), 75)
            ),
            averageValue = 73,
            maxValue = 78,
            unit = "bpm"
        )
    }

    override fun getHealthInfos(): List<HealthInfoModel> {
        return listOf(
            HealthInfoModel(
                R.drawable.ic_heart,
                "Heart",
                listOf(
                    Pair(LocalDateTime.now().minusDays(7), 70),
                    Pair(LocalDateTime.now().minusDays(6), 72),
                    Pair(LocalDateTime.now().minusDays(5), 90),
                    Pair(LocalDateTime.now().minusDays(4), 75),
                    Pair(LocalDateTime.now().minusDays(3), 80),
                    Pair(LocalDateTime.now().minusDays(2), 85),
                    Pair(LocalDateTime.now().minusDays(1), 78),
                    Pair(LocalDateTime.now(), 75)
                ),
                73,
                78,
                "bpm"
            ),
            HealthInfoModel(
                R.drawable.ic_weight,
                "Weight",
                listOf(
                    Pair(LocalDateTime.now().minusDays(7), 70),
                    Pair(LocalDateTime.now().minusDays(6), 72),
                    Pair(LocalDateTime.now().minusDays(5), 90),
                    Pair(LocalDateTime.now().minusDays(4), 75),
                    Pair(LocalDateTime.now().minusDays(3), 80),
                    Pair(LocalDateTime.now().minusDays(2), 85),
                    Pair(LocalDateTime.now().minusDays(1), 78),
                    Pair(LocalDateTime.now(), 75)
                ),
                73,
                78,
                "kg"
            ),
        )
    }
}