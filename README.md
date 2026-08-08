<div align="center">

# Digi Droid

### Android diagnostics, ROM testing, and maintainer reporting app

Digi Droid is a native Android app for custom ROM users, testers, maintainers, kernel developers, and device bring-up developers. It collects important device, ROM, kernel, battery, storage, network, sensor, and system information in one place, then helps users generate clean reports for debugging and ROM support.

<br>

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-6750A4?style=for-the-badge&logo=materialdesign&logoColor=white)

</div>

---

## About Digi Droid

Digi Droid is built for the Android custom ROM world.

When users report bugs, maintainers usually need proper device details, ROM information, kernel version, battery state, storage state, network state, logs, reproduction steps, and sometimes a ROM ZIP checksum. Without this information, debugging becomes slow and messy.

Digi Droid solves that by giving users one simple app to collect diagnostics and generate useful maintainer-friendly reports.

It is useful for:

- ROM testers
- Custom ROM users
- Device maintainers
- Kernel developers
- Device tree developers
- Android support groups
- Bug report collection
- Release verification

---

## Features

### Device Dashboard

The dashboard gives a quick overview of the device and current build.

It shows important information such as:

- Manufacturer
- Model
- Device codename
- Android version
- SDK version
- Security patch
- Build type
- Build fingerprint
- Kernel version
- Battery status
- Storage status
- RAM status
- Root/system status

---

### ROM Information

Digi Droid reads Android build and ROM-related information from system APIs and available system properties.

It helps users and maintainers identify:

- Android release
- SDK level
- Security patch
- Build type
- Build tags
- Build fingerprint
- Product device
- Product model
- Product manufacturer
- Vendor security patch, if available
- ROM/build identity exposed by the device

This is useful when reporting bugs after flashing a new ROM build.

---

### Kernel Information

The kernel section shows information useful for kernel testing and ROM bring-up work.

It can display:

- Kernel version
- Kernel release
- Architecture
- Supported ABIs
- Device uptime
- SELinux status
- Kernel command line, if readable
- Kernel-related system information available on the device

Some kernel details may not be readable on every Android version because of SELinux, vendor restrictions, or missing permissions.

---

### Battery Diagnostics

The battery section helps users report power, charging, heating, and battery-drain issues with proper context.

It shows:

- Battery level
- Charging status
- Charging source
- Battery health
- Battery temperature
- Battery voltage
- Battery technology

---

### Storage and Memory

Digi Droid shows storage and RAM information that can help identify low-space or low-memory problems.

It includes:

- Total internal storage
- Used storage
- Free storage
- Total RAM
- Available RAM
- Low memory state

---

### Network Diagnostics

The network section helps with Wi-Fi, mobile data, Bluetooth, and general connectivity debugging.

It can show:

- Wi-Fi status
- Mobile network status
- Network transport type
- Local IP address, where available
- Operator information, where available
- Bluetooth status hints
- Airplane mode status

Some network values depend on Android permissions and device restrictions.

---

### Sensor Information

Digi Droid lists sensors exposed by the device.

This helps testers confirm whether the ROM properly detects hardware such as:

- Accelerometer
- Gyroscope
- Proximity sensor
- Light sensor
- Magnetometer
- Barometer
- Vendor-specific sensors

---

### Root and System Checks

Digi Droid includes system status checks useful for custom ROM environments.

It can detect or report hints for:

- `su` availability
- Magisk package presence
- KernelSU package presence
- APatch package presence
- SELinux state
- Verified boot related properties, where available
- Bootloader / flash lock related hints, where readable

The app does not guess restricted values. If Android blocks access, Digi Droid shows the value as unavailable or restricted.

---

### Bug Report Generator

Digi Droid includes a structured bug report generator for ROM testers.

Users can select an issue type, describe the problem, add reproduction steps, add extra notes, and generate a clean report with device and ROM details.

Supported issue types include:

- Bootloop
- Battery drain
- Heating
- Camera issue
- Bluetooth issue
- Wi-Fi issue
- Mobile network issue
- Fingerprint issue
- Performance issue
- Random reboot
- App crash
- Charging issue
- Other

Generated reports can be copied or shared directly.

---

### Logs Helper

The logs section helps users collect available diagnostic text from the device.

Depending on Android restrictions, Digi Droid can help with:

- Recent log output
- Reboot reason information
- `dmesg` output where accessible
- Copy/share actions for diagnostics

Android restricts full log access for normal apps. Digi Droid only shows what the device allows.

---

### ZIP Checksum Verification

The ZIP Verify section calculates SHA-256 checksums for selected files.

This is useful for checking:

- ROM ZIP files
- Kernel ZIP files
- Recovery images
- Firmware packages
- Flashable builds
- Test builds shared with users

The app shows:

- File name
- File size
- SHA-256 checksum

The checksum can be copied or shared.

---

### Maintainer Mode

Maintainer Mode unlocks advanced inspection tools for developers and ROM maintainers.

It gives access to deeper build and system information such as:

- System properties viewer
- Advanced ROM/build details
- Additional diagnostics useful for maintainers

This keeps the app simple for normal users while still being powerful for Android developers.

---

## App Sections

Digi Droid includes these main sections:

- Dashboard
- Device
- ROM
- Kernel
- Battery
- Storage
- Network
- Sensors
- Root & System
- Logs
- Report
- ZIP Verify
- System Properties
- Settings
- About

---

## Privacy

Digi Droid is designed as an offline diagnostics app.

It does not use:

- Login
- Accounts
- Backend server
- Cloud upload
- Analytics service
- Automatic report submission
- Silent log sharing

Reports, logs, and checksums stay on the device unless the user manually copies or shares them.

---

## Android Limitations

Android restricts access to some system information for privacy and security.

Because of that, some values may be unavailable depending on:

- Android version
- ROM source
- SELinux policy
- Vendor implementation
- Root access
- App permissions
- Device-specific property exposure

Examples:

- Full logcat access may be restricted.
- `dmesg` usually requires root.
- Wi-Fi SSID may require permission on newer Android versions.
- Bootloader state may not be exposed on every device.
- Some system properties are hidden or vendor-specific.
- Kernel command line may not be readable on production builds.

Digi Droid handles these cases clearly instead of showing fake or guessed values.

---

## Tech Stack

Digi Droid is built with modern native Android tools:

- Kotlin
- Jetpack Compose
- Material 3
- AndroidX
- Android system APIs
- Storage Access Framework
- Kotlin Coroutines
- Gradle Kotlin DSL

---

## Project Structure

```text
digi-droid/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/manishkishore/digidroid/
│           │   ├── MainActivity.kt
│           │   ├── data/
│           │   │   ├── provider/
│           │   │   └── repository/
│           │   ├── model/
│           │   ├── ui/
│           │   │   ├── component/
│           │   │   ├── screen/
│           │   │   └── theme/
│           │   └── util/
│           └── res/
├── build.gradle.kts
├── settings.gradle.kts
├── .gitignore
└── README.md
```

---

## Build Requirements

To build Digi Droid, use:

- Android Studio
- JDK 17
- Android SDK installed
- Android device or emulator
- Internet connection for Gradle dependency sync

A physical Android device is recommended because many diagnostics depend on real hardware and system properties.

---

## Build and Run

Clone the repository:

```bash
git clone https://github.com/manishkishore92/digi-droid.git
cd digi-droid
```

Open the project in Android Studio.

Then:

1. Let Gradle sync finish.
2. Connect an Android device.
3. Select the `app` run configuration.
4. Click Run.
5. Open Digi Droid on the device.

To build from terminal:

```bash
./gradlew assembleDebug
```

The debug APK will be generated at:

```text
app/build/outputs/apk/debug/
```

---

## Best Testing Environment

Digi Droid works best on:

- A physical Android device
- A custom ROM build
- Android 10 or newer
- A device with normal system property access
- Rooted device only when testing root-only diagnostics

The app works without root, but root-only information may not be available.

---

## Report Output

Digi Droid reports are designed to be easy to paste into GitHub issues, Telegram groups, or maintainer chats.

A generated report can include:

```markdown
## Digi Droid Report

### Issue
Category: Camera issue
Description: Camera crashes when switching to video mode.

### Reproduction Steps
1. Open Camera
2. Switch to video mode
3. App closes automatically

### Device
Manufacturer: Xiaomi
Model: Redmi Note 10 Pro
Device: sweet

### ROM
Android: 15
Build Type: userdebug
Security Patch: 2026-07-05

### Kernel
Kernel: 4.14.x
SELinux: Enforcing

### Battery
Level: 78%
Charging: Not charging
Temperature: 34°C

### Notes
Issue happens after clean flash too.
```

---

## Checksum Output

The ZIP verifier generates SHA-256 output for release and test builds.

```text
File: lineage-22.2-UNOFFICIAL-sweet.zip
Size: 2.1 GB
SHA-256: 8f4b7d1c0e3a9e8c0e8f6b6e7f7a9a4c2e1d0b7a6f5c4d3b2a1e9f8c7d6b5a4
```

---

## Development Focus

Digi Droid focuses on practical Android ROM testing and diagnostics.

The app is built around real maintainer needs:

- Knowing exactly what build a tester is using
- Getting structured bug reports
- Checking kernel and system status
- Verifying release ZIP checksums
- Reducing repeated questions in support groups
- Making tester feedback more useful

---

## Contributing

Contributions are welcome if they improve the app for Android ROM users, testers, and maintainers.

Good areas to improve:

- More diagnostics providers
- Better report formatting
- More ROM-specific property detection
- Better root/system checks
- UI improvements
- Device-specific compatibility fixes
- Documentation improvements

Please test changes on a real Android device when possible.

---

## Maintainer

Created and maintained by **Manish Kishore**.

GitHub: [@manishkishore92](https://github.com/manishkishore92)

---

<div align="center">

**Digi Droid**  
Built for Android ROM users, testers, maintainers, and developers.

</div>
