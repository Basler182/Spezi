package eu.schk.bluetooth_connectivity.omron

import java.util.UUID


val omronServiceMap = OmronService.entries.associateBy { it.uuid }

fun isServiceUUIDInOmronServices(serviceUUID: UUID): Boolean {
    return omronServiceMap.containsKey(serviceUUID)
}

fun getOmronDescriptorNameByUUID(descriptorUUID: UUID): String? {
    return OmronDescriptors.entries.firstOrNull { it.uuid == descriptorUUID }?.descriptorName
}

fun getOmronServiceNameByDescriptorUUID(descriptorUUID: UUID): String? {
    return OmronService.entries.firstOrNull { service ->
        service.descriptors.any { it.uuid == descriptorUUID }
    }?.serviceName
}


enum class OmronDescriptors(val uuid: UUID, val descriptorName: String) {
    MODEL_NUMBER_CHARACTERISTICS(
        UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb"),
        "Model Number String"
    ),

    BATTERY_LEVEL(
        UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb"),
        "Battery Level"
    ),

    CURRENT_TIME(
        UUID.fromString("00002a2b-0000-1000-8000-00805f9b34fb"),
        "Current Time"
    ),

    BLOOD_PRESSURE_FEATURE(
        UUID.fromString("00002a49-0000-1000-8000-00805f9b34fb"),
        "Blood Pressure Feature"
    ),
    BLOOD_PRESSURE_MEASUREMENT(
        UUID.fromString("00002a35-0000-1000-8000-00805f9b34fb"),
        "Blood Pressure Measurement"
    ),
}


enum class OmronService(
    val uuid: UUID,
    val serviceName: String,
    val descriptors: List<OmronDescriptors>
) {
    DEVICE_INFORMATION(
        UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb"),
        "Device Information Service",
        listOf(OmronDescriptors.MODEL_NUMBER_CHARACTERISTICS)
    ),
    BATTERY(
        UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb"),
        "Battery Service",
        listOf(OmronDescriptors.BATTERY_LEVEL)
    ),
    CURRENT_TIME(
        UUID.fromString("00001805-0000-1000-8000-00805f9b34fb"),
        "Current Time Service",
        listOf(OmronDescriptors.CURRENT_TIME)
    ),

    BLOOD_PRESSURE(
        UUID.fromString("00001810-0000-1000-8000-00805f9b34fb"),
        "Blood Pressure Service",
        listOf(
            OmronDescriptors.BLOOD_PRESSURE_FEATURE, // 0500
            OmronDescriptors.BLOOD_PRESSURE_MEASUREMENT
        )
    )
}