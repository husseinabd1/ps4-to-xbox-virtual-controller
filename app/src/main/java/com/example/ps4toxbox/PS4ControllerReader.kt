package com.example.ps4toxbox

import android.content.Context
import android.hardware.input.InputManager
import android.util.Log
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent

class PS4ControllerReader(
    context: Context,
    private val onButtonPressed: (Int, Int) -> Unit,
    private val onAxisChanged: (Int, Float) -> Unit
) {

    private val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
    private val TAG = "PS4Reader"
    private var ps4DeviceId: Int? = null

    fun findPS4Controller(): Boolean {
        val devices = inputManager.inputDeviceIds
        for (deviceId in devices) {
            val device = inputManager.getInputDevice(deviceId)
            if (device != null && isPS4Controller(device)) {
                ps4DeviceId = deviceId
                Log.d(TAG, "Found PS4 controller: ${device.name} (ID: $deviceId)")
                return true
            }
        }
        Log.w(TAG, "No PS4 controller found")
        return false
    }

    private fun isPS4Controller(device: InputDevice): Boolean {
        val name = device.name.toLowerCase()
        return name.contains("ps4") || 
               name.contains("dualshock") || 
               name.contains("wireless controller") ||
               name.contains("playstation")
    }

    fun handleKeyEvent(event: KeyEvent): Boolean {
        if (event.source and InputDevice.TOOL_TYPE_UNKNOWN == 0) {
            val mapped = mapPS4Key(event.keyCode)
            if (mapped != null) {
                onButtonPressed(mapped, event.action)
                Log.d(TAG, "PS4 key ${event.keyCode} -> Xbox key $mapped (${event.action})")
                return true
            }
        }
        return false
    }

    fun handleMotionEvent(event: MotionEvent): Boolean {
        if (event.source and InputDevice.TOOL_GENERIC_2 != 0) {
            val x = event.getAxisValue(MotionEvent.AXIS_X)
            val y = event.getAxisValue(MotionEvent.AXIS_Y)
            val rx = event.getAxisValue(MotionEvent.AXIS_RX)
            val ry = event.getAxisValue(MotionEvent.AXIS_RY)
            val lt = event.getAxisValue(MotionEvent.AXIS_Z)
            val rt = event.getAxisValue(MotionEvent.AXIS_RZ)

            if (x != 0f) onAxisChanged(MotionEvent.AXIS_X, x)
            if (y != 0f) onAxisChanged(MotionEvent.AXIS_Y, y)
            if (rx != 0f) onAxisChanged(MotionEvent.AXIS_RX, rx)
            if (ry != 0f) onAxisChanged(MotionEvent.AXIS_RY, ry)
            if (lt != 0f) onAxisChanged(MotionEvent.AXIS_Z, lt)
            if (rt != 0f) onAxisChanged(MotionEvent.AXIS_RZ, rt)

            return true
        }
        return false
    }

    private fun mapPS4Key(ps4Code: Int): Int? {
        // DualShock 4 HID button codes -> Xbox key codes
        return when (ps4Code) {
            96 -> KeyEvent.KEYCODE_BUTTON_Y      // Triangle
            97 -> KeyEvent.KEYCODE_BUTTON_A      // Cross
            98 -> KeyEvent.KEYCODE_BUTTON_B      // Circle
            99 -> KeyEvent.KEYCODE_BUTTON_X      // Square
            102 -> KeyEvent.KEYCODE_BUTTON_L1    // L1
            103 -> KeyEvent.KEYCODE_BUTTON_R1    // R1
            104 -> KeyEvent.KEYCODE_BUTTON_SELECT // Share
            105 -> KeyEvent.KEYCODE_BUTTON_START  // Options
            106 -> KeyEvent.KEYCODE_BUTTON_MODE   // PS button
            107 -> KeyEvent.KEYCODE_BUTTON_THUMBL // Left stick click
            108 -> KeyEvent.KEYCODE_BUTTON_THUMBR // Right stick click
            else -> null
        }
    }

    fun getPS4DeviceId(): Int? = ps4DeviceId
}
