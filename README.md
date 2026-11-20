# NX-Uplink

> **Official Android companion app for the NX hardware ecosystem by Ghost3379.**

NX-Uplink is a high-performance native Android application (Kotlin + Jetpack Compose) designed to pair with, monitor, and configure hardware devices across the NX ecosystem:
- **NX-ISD**: Tactical Biometric Field Watch (ESP32-S3, GC9A01 Round IPS, 4x4 WS2812B NeoPixel matrix, RV-3028 RTC, MAX17048 Fuel Gauge).
- **NX-Edge**: M.2 2260 Key B Autonomous Edge Blade (Bare ESP32-S3 IC rotated 45°, Dual RF Ignion + U.FL, BNO085 9-DOF IMU, MAX98357A I2S Class-D Amp, MicroSD).

---

## Features
- **Device Fleet Registry**: Clean start screen and sliding sidepanel drawer for seamless device discovery, pairing, and status overview.
- **Hardware Telemetry Dashboard**: OLED black UI with tactical amber aesthetics, minimal design, zero bloat.
- **WayneTech Subsystem Matrix**:
  - `General`: Power telemetry & storage provenance.
  - `Peripherie`: Real-Time Clock synchronization & LED matrix control.
  - `Notifications`: BLE alert profiles and haptic triggers.
  - `Health`: Biometrics and PPG vitals monitoring.
  - `Field Manual`: Operating references, battery runtimes, and lever navigation.
- **Foldable Optimized**: Tailored for the Samsung Galaxy Z Flip series (Flex Window outer cover screen support & vertical unfolded viewport).

---

## Releases & Downloads
Precompiled Android APKs are available directly in this repository:
- **Latest Release**: [`NX-Uplink-v1.1.apk`](NX-Uplink-v1.1.apk) (15.7 MB)

---

## Development Setup
- **Framework**: Native Android (Kotlin 1.9.24, Jetpack Compose BOM 2024.06.00)
- **Minimum SDK**: Android 8.0 (API 26)
- **Target SDK**: Android 14 (API 34)
- **Build Tools**: 36.0.0
- **JDK Requirement**: Java 17

### Building from Source
```bash
# Debug APK
./gradlew assembleDebug

# Install directly to connected device over ADB
adb install -r app/build/outputs/apk/debug/app-debug.apk
```
