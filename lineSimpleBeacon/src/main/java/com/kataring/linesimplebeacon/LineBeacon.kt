package com.kataring.linesimplebeacon

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.*
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

class LineBeacon(context: Context, beaconSettings: BeaconSettings?) {
    private lateinit var bleAdapter: BluetoothAdapter
    private lateinit var bleLeAdvertiser: BluetoothLeAdvertiser
    private lateinit var bleManager: BluetoothManager

    private val LINE_BEACON_SERVICE_UUID = "0000fe6f-0000-1000-8000-00805f9b34fb"
    private val LINE_BEACON_ADVERTISE_DATA_HEX_STRING = "02%s7f%s"

    private var isAdvertising = false
    private var mBeaconSettings: BeaconSettings? = beaconSettings

    private val TAG = "LineBeacon"

    init {
        bleManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bleAdapter = bleManager.getAdapter()
    }

    private var AdvertiseCallback = object : AdvertiseCallback() {
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
            super.onStartSuccess(settingsInEffect)
            isAdvertising = true
            Log.d(TAG, "LE Advertise Started: $settingsInEffect")
        }

        override fun onStartFailure(errorCode: Int) {
            super.onStartFailure(errorCode)

            var reason = ""

            when (errorCode) {
                ADVERTISE_FAILED_FEATURE_UNSUPPORTED -> {
                    reason = "ADVERTISE_FAILED_FEATURE_UNSUPPORTED"
                    isAdvertising = false
                }
                ADVERTISE_FAILED_TOO_MANY_ADVERTISERS -> {
                    reason = "ADVERTISE_FAILED_TOO_MANY_ADVERTISERS"
                    isAdvertising = false
                }
                ADVERTISE_FAILED_ALREADY_STARTED -> {
                    reason = "ADVERTISE_FAILED_ALREADY_STARTED"
                    isAdvertising = true
                }
                ADVERTISE_FAILED_DATA_TOO_LARGE -> {
                    reason = "ADVERTISE_FAILED_DATA_TOO_LARGE"
                    isAdvertising = false
                }
                ADVERTISE_FAILED_INTERNAL_ERROR -> {
                    reason = "ADVERTISE_FAILED_INTERNAL_ERROR"
                    isAdvertising = false
                }
                else -> {
                    reason = "unknown"
                    isAdvertising = false
                }
            }
            Log.d(TAG, "LE Advertise Failed: $errorCode - $reason")
        }
    }

    fun createAdvertiseData(hwid: String, deviceMessage: String = "00") {
        if (deviceMessage.length > 26 || deviceMessage.length % 2 != 0) {
            Log.d(TAG, "LINE Beacon Device Message is invalid. " + deviceMessage.length)
            return
        }

        val regex = Regex("^([0-9a-fA-F]{2}){1,13}$")
        if (!regex.matches(deviceMessage)) {
            Log.d(TAG, "LINE Beacon Device Message is not Hex.")
            return
        }

        val serviceUuid = ParcelUuid.fromString(LINE_BEACON_SERVICE_UUID)

        val adviserSettings = mBeaconSettings?.let {
            AdvertiseSettings.Builder()
                .setTxPowerLevel(it.TxPowerLevel)
                .setAdvertiseMode(it.AdvertiseMode)
                .setConnectable(it.IsConnectable)
                .setTimeout(it.Timeout)
                .build()
        }

        val advertiseData = mBeaconSettings?.let {
            AdvertiseData.Builder()
                .addServiceUuid(serviceUuid)
                .addServiceData(
                    serviceUuid, hexStringToByteArray(
                        String.format(
                            LINE_BEACON_ADVERTISE_DATA_HEX_STRING,
                            hwid, deviceMessage
                        )
                    )
                )
                .setIncludeTxPowerLevel(it.IsIncludeTxPowerLevel)
                .setIncludeDeviceName(it.IsIncludeDeviceName)
                .build()
        }

        val resData = mBeaconSettings?.let {
            AdvertiseData.Builder()
                .setIncludeDeviceName(it.IsIncludeDeviceName)
                .build()
        }

        if (isAdvertising) {
            stopAdvertising()
        }

        bleLeAdvertiser = bleAdapter.getBluetoothLeAdvertiser()
        bleLeAdvertiser.startAdvertising(
            adviserSettings, advertiseData, resData, AdvertiseCallback
        )
    }

    fun stopAdvertising() {
        if (isAdvertising) {
            bleLeAdvertiser.stopAdvertising(AdvertiseCallback)
            isAdvertising = false
        }
    }

    private fun hexStringToByteArray(hexString: String): ByteArray {
        val array = ByteArray(hexString.length / 2)
        for (index in 0 until array.count()) {
            val pointer = index * 2
            array[index] = hexStringToByte(hexString.substring(pointer, pointer + 2))
        }
        return array
    }

    private fun hexStringToByte(hexString: String) = hexString.toInt(16).toByte()
}
