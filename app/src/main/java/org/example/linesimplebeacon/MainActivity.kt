package org.example.linesimplebeacon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kataring.linesimplebeacon.BeaconSettings
import com.kataring.linesimplebeacon.LineBeacon

class MainActivity : AppCompatActivity() {
    private val LINE_BEACON_HWID = "<user-line-beacon-hwid>"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settings = BeaconSettings()
        val beacon = LineBeacon(settings)
        beacon.createAdvertiseData(LINE_BEACON_HWID)
        setContentView(R.layout.activity_main)
    }
}
