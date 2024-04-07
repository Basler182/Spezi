package eu.schk.bluetooth_connectivity.omron

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.nio.ByteBuffer
import java.util.LinkedList
import java.util.Queue

class BluetoothManager(private val context: Context) {

    private val readQueue: Queue<BluetoothGattCharacteristic> = LinkedList()
    private var isReading: Boolean = false
    private lateinit var bluetoothGatt: BluetoothGatt

    private fun checkBluetoothPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context, Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun readCharacteristic(characteristic: BluetoothGattCharacteristic) {
        readQueue.add(characteristic)
        if (!isReading) {
            startNextRead()
        }
    }

    private fun startNextRead() {
        if (readQueue.isNotEmpty() && checkBluetoothPermission()) {
            isReading = true
            bluetoothGatt.readCharacteristic(readQueue.peek())
        }
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice) {
        if (!checkBluetoothPermission()) {
            return
        }
        device.connectGatt(context, true, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(
                gatt: BluetoothGatt, status: Int, newState: Int
            ) {
                super.onConnectionStateChange(
                    gatt, status, newState
                )
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    bluetoothGatt = gatt
                    gatt.discoverServices()
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.i(
                        "MainActivity", "Verbindung verloren oder getrennt"
                    )
                }
            }

            override fun onServicesDiscovered(
                gatt: BluetoothGatt, status: Int
            ) {
                super.onServicesDiscovered(gatt, status)
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    for (service in gatt.services) {
                        if (isServiceUUIDInOmronServices(
                                service.uuid
                            )
                        ) {
                            val omronService =
                                OmronService.entries.first { it.uuid == service.uuid }
                            service.characteristics.forEach { characteristic ->
                                if (omronService.descriptors.any { it.uuid == characteristic.uuid }) {
                                    if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_INDICATE > 0) {
                                        bluetoothGatt.setCharacteristicNotification(
                                            characteristic, true
                                        )
                                    }
                                    readCharacteristic(
                                        characteristic
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Log.i(
                        "MainActivity", "Service Discovery nicht erfolgreich"
                    )
                }
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                value: ByteArray,
                status: Int
            ) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    val hexString = value.joinToString("") {
                        "%02x".format(it)
                    }
                    val valueString = String(value)
                    var valueInt = 0
                    try {
                        valueInt = ByteBuffer.wrap(value).int
                    } catch (_: Exception) {
                    }

                    var valueFloat = 0.0f
                    try {
                        valueFloat = ByteBuffer.wrap(value).float
                    } catch (_: Exception) {
                    }

                    val descriptorName = getOmronDescriptorNameByUUID(
                        characteristic.uuid
                    )

                    val serviceName = getOmronServiceNameByDescriptorUUID(
                        characteristic.uuid
                    )

                    Log.i(
                        "MainActivity",
                        "Service Name: $serviceName Descriptor Name: $descriptorName with Value String: $valueString  Value Int:  $valueInt  Value Float:  $valueFloat  Hex String: $hexString"
                    )

                    readQueue.remove()
                    isReading = false
                    startNextRead()
                }
            }
        })
    }

    fun close() {
        if (checkBluetoothPermission()) {
            bluetoothGatt.close()
        }
    }
}