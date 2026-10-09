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
    private val tag = "PS4ControllerReader"
    private var deviceId: Int? = null

    fun findPS4Controller(): Boolean {
        val ids = inputManager.inputDeviceIds
        for (id in ids) {
            val device = inputManager.getInputDevice(id)
            if (device != null && isPS4Controller(device)) {
                deviceId = id
                Log.d(tag, "PS4 device found: ${device.name}")
                return true
            }
        }
        return false
    }

    private fun isPS4Controller(device: InputDevice): Boolean {
        val name = device.name.lowercase()
        return name.contains("ps4") ||
            name.contains("dualshock") ||
            name.contains("playstation") ||
            name.contains("wireless controller")
    }

    fun handleKeyEvent(event: KeyEvent): Boolean {
        val mapped = mapPS4Key(event.keyCode)
        if (mapped != null) {
            onButtonPressed(mapped, event.action)
            Log.d(tag, "PS4 key ${event.keyCode} -> Xbox key $mapped action=${event.action}")
            return true
        }
        return false
    }

    fun handleMotionEvent(event: MotionEvent): Boolean {
        val axisMap = listOf(
            MotionEvent.AXIS_X,
            MotionEvent.AXIS_Y,
            MotionEvent.AXIS_Z,
            MotionEvent.AXIS_RX,
            MotionEvent.AXIS_RY,
            MotionEvent.AXIS_RZ
        )

        for (axis in axisMap) {
            val value = event.getAxisValue(axis)
            if (value != 0f) {
                onAxisChanged(axis, value)
                Log.d(tag, "Axis $axis = $value")
            }
        }
        return true
    }

    private fun mapPS4Key(ps4Code: Int): Int? {
        return when (ps4Code) {
            96 -> KeyEvent.KEYCODE_BUTTON_Y
            97 -> KeyEvent.KEYCODE_BUTTON_A
            98 -> KeyEvent.KEYCODE_BUTTON_B
            99 -> KeyEvent.KEYCODE_BUTTON_X
            102 -> KeyEvent.KEYCODE_BUTTON_L1
            103 -> KeyEvent.KEYCODE_BUTTON_R1
            104 -> KeyEvent.KEYCODE_BUTTON_SELECT
            105 -> KeyEvent.KEYCODE_BUTTON_START
            106 -> KeyEvent.KEYCODE_BUTTON_MODE
            107 -> KeyEvent.KEYCODE_BUTTON_THUMBL
            108 -> KeyEvent.KEYCODE_BUTTON_THUMBR
            else -> null
        }
    }
}
