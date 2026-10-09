# PS4 to Xbox (No-root fallback)

This project is a practical Android template for experimenting with PS4-to-Xbox mapping on Android TV / Android devices.

Important:
- This version does not create a true Xbox gamepad device without root + uinput.
- Full emulation of a real Xbox controller requires rooted device access and kernel-level input injection.
- This app is useful as a mapping prototype and as a foundation for a rooted implementation.

## What this project includes
- Android app UI
- AccessibilityService hook for a fallback mechanism
- PS4 button mapping to Xbox-like key codes
- Logging for testing and debugging

## Project structure

```text
app/
  src/main/
    AndroidManifest.xml
    java/com/example/ps4toxbox/
      MainActivity.kt
      GamepadService.kt
      ControllerMapper.kt
      VirtualGamepadManager.kt
    res/
      layout/activity_main.xml
      values/strings.xml
      values/themes.xml
      xml/accessibility_service_config.xml
```

## Build

```bash
./gradlew assembleDebug
```

## How to test
1. Enable the Accessibility Service in Android Settings.
2. Open the app.
3. Tap "Open Accessibility Settings".
4. Enable the service.
5. Connect a DualShock 4 controller.
6. Check logs in Logcat.

## Real solution
To fully emulate an Xbox controller for GTA San Andreas / Android TV, a rooted environment and native `uinput` injection is required.

Without that, Android will not accept a fake Xbox controller as a real gamepad in most cases.
