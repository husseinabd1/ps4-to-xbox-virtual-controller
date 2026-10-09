# PS4 to Xbox Virtual Controller

This project is a prototype for Android TV / Android 8.0 devices. It maps DualShock 4 buttons to Xbox-like key codes and logs the transformation for testing.

Important reality check:
- Full emulation of a real Xbox gamepad for GTA requires root + /dev/uinput + kernel permissions.
- Without root, Android cannot usually present a fake Xbox pad as a real gamepad to the system.
- This project therefore includes a practical fallback layer and a native prototype for rooted devices.

## Supported build target
- Android API 26+
- armeabi-v7a / ARM32
- NDK + CMake

## Structure

```text
app/
  src/main/
    AndroidManifest.xml
    cpp/
      CMakeLists.txt
      virtual_xbox_device.cpp
    java/com/example/ps4toxbox/
      MainActivity.kt
      GamepadService.kt
      ControllerMapper.kt
      PS4ControllerReader.kt
      GamepadMonitor.kt
      ButtonMap.kt
      VirtualGamepadManager.kt
    res/
      layout/activity_main.xml
      values/strings.xml
      values/themes.xml
      xml/accessibility_service_config.xml
```

## Build steps

```bash
./gradlew assembleDebug
```

## Notes
- If the device is rooted, the NDK code can create a virtual Xbox input device using `/dev/uinput`.
- If the device is not rooted, this is only a fallback and will not fully emulate a real Xbox controller.
