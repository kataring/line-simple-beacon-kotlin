package com.kataring.linesimplebeacon

import android.bluetooth.le.*

class BeaconSettings {
    var Type: Int = BEACON_TYPE_LINE
    var HWID: String = ""
    var IsIncludeTxPowerLevel: Boolean = false
    var IsIncludeDeviceName: Boolean = false
    var TxPowerLevel: Int
    var AdvertiseMode: Int
    var IsConnectable: Boolean
    var Timeout: Int

    companion object {
        const val BEACON_TYPE_LINE = 1
    }

    init {
        TxPowerLevel = AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM
        AdvertiseMode = AdvertiseSettings.ADVERTISE_MODE_BALANCED
        IsConnectable = false
        Timeout = 0
    }
}
