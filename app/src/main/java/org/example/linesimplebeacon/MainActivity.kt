package org.example.linesimplebeacon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kataring.linesimplebeacon.*

class MainActivity : AppCompatActivity() {
    private val LINE_BEACON_HWID = "<your_beacon_hwid>"
    private lateinit var mBeacon: LineBeacon
    private val mSettings = BeaconSettings()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBeacon = LineBeacon(this, mSettings)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        mBeacon.createAdvertiseData(LINE_BEACON_HWID, "1234")
    }

}
