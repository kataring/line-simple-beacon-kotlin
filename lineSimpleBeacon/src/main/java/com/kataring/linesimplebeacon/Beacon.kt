package com.kataring.linesimplebeacon

import android.bluetooth.le.*

interface Beacon {
    fun createAdvertiseData(): AdvertiseData?
}
