# LINE Simple Beacon for Kotlin

Sample Application for LINE Simple Beacon

### How to use

```kotlin
val LINE_BEACON_HWID = "<your_beacon_hwid>"
val mSettings = BeaconSettings()
val mBeacon = LineBeacon(this, mSettings)

// start
mBeacon.createAdvertiseData(LINE_BEACON_HWID, "1234")

// stop
mBeacon.stopAdvertising()
```

### LICENSE

MIT
