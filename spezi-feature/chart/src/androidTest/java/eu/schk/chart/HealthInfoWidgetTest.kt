package eu.schk.chart

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import eu.schk.chart.repository.DefaultHealthInfoRepository
import eu.schk.chart.repository.HealthInfoModel
import eu.schk.chart.repository.HealthInfoRepository
import eu.schk.chart.components.HealthInfoWidget
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class HealthInfoWidgetTest {

    private val mockHealthInfoRepository: HealthInfoRepository = mock()
    private lateinit var viewModel: HealthPerformanceViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        whenever(mockHealthInfoRepository.getHealthInfo()).thenReturn(DefaultHealthInfoRepository().getHealthInfo())
        viewModel = HealthPerformanceViewModel(mockHealthInfoRepository)
    }

    @Test
    fun healthInfoWidget_displaysCorrectData() {
        val heartRateInfo = mockHealthInfoRepository.getHealthInfo()

        composeTestRule.setContent {
            HealthInfoWidget(heartRateInfo)
        }

        composeTestRule.onNodeWithText("Heart").assertExists()
        composeTestRule.onNodeWithContentDescription("Health Info Chart").assertExists()
    }

    @Test
    fun healthInfoWidget_displaysNotEnoughDataMessage() {

        whenever(mockHealthInfoRepository.getHealthInfo()).thenReturn(
            HealthInfoModel(
                iconDrawable = R.drawable.ic_arrow_bottom_left,
                healthType = "Heart",
                valueReadings = emptyList(),
                averageValue = 73,
                maxValue = 78,
                unit = "bpm"
            )
        )
        composeTestRule.setContent {
            HealthInfoWidget(mockHealthInfoRepository.getHealthInfo())
        }

        composeTestRule.onNodeWithText("No data available").assertExists()
    }
}
