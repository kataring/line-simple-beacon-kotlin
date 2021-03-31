package com.kataring.linesimplebeacon

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.*

interface Beacon {
    fun createAdvertiseData(): AdvertiseData?
}
