package eu.schk.bluetooth_connectivity

import eu.schk.bluetooth_connectivity.omron.BloodPressureMeasurement
import org.junit.Assert
import org.junit.Test

class BloodPressureMeasurementKtTest {

    @Test
    fun testBloodPressureMeasurementFromByteArray() {
        val byteArray =
            byteArrayOf(30, -126, 0, 87, 0, 101, 0, -24, 7, 4, 14, 13, 6, 15, 71, 0, 1, 0, 0)
        val result = BloodPressureMeasurement.fromByteArray(byteArray)

        Assert.assertNotNull(result)

        Assert.assertEquals(130.0f, result.systolic, 0.01f)
        Assert.assertEquals(87.0f, result.diastolic, 0.01f)
        Assert.assertEquals(71f, result.pulseRate, 0.01f)

        Assert.assertEquals(4, result.timestampMonth)
        Assert.assertEquals(14, result.timestampDay)
        Assert.assertEquals(13, result.timeStampHour)
        Assert.assertEquals(6, result.timeStampMinute)
    }
}