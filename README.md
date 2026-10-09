# PS4 Advanced Monitor + Mapping + Logs

This version is intended for a rooted or advanced Android environment for testing and monitoring DualShock 4 input.

## What this version does
- Detects PS4 controller
- Monitors input events
- Maps PS4 buttons to Xbox-style names
- Shows detailed logs in UI
- Runs a background service

## Important note
This is a monitoring + mapping layer. It does not create a full virtual Xbox device on most Android devices unless root + uinput access is available.

## PS4 -> Xbox mapping
- Triangle -> Y
- Cross -> A
- Circle -> B
- Square -> X
- L1 -> LB
- R1 -> RB
- Share -> Back
- Options -> Start
- PS -> Guide

## Build

```bash
./gradlew assembleDebug
```

## Install

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```
