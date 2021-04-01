package org.example.linesimplebeacon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kataring.linesimplebeacon.*

class MainActivity : AppCompatActivity() {
    private val LINE_BEACON_HWID = "014731b746"
    private lateinit var mbeacon: LineBeacon
    private val mSettings = BeaconSettings()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbeacon = LineBeacon(this, mSettings)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        mbeacon.createAdvertiseData(LINE_BEACON_HWID, "1234")
    }

}
