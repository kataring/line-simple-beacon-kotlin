# LINE Simple Beacon Kotlin Sample Application

### How to use

```kotlin
val LINE_BEACON_HWID = "<your_beacon_hwid>"
val mSettings = BeaconSettings()
val mBeacon = LineBeacon(this, mSettings)
mBeacon.createAdvertiseData(LINE_BEACON_HWID, "1234")
```

### LICENSE

MIT
