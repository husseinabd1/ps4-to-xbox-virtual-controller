package com.example.ps4toxbox

import android.content.Context
import android.hardware.input.InputManager
import android.util.Log
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent

class PS4InputReader(context: Context) {
    private val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
    private val tag = "PS4InputReader"

    fun scanForPS4(): InputDevice? {
        val ids = inputManager.inputDeviceIds
        for (id in ids) {
            val device = inputManager.getInputDevice(id)
            if (device != null && isPS4(device)) {
                Log.d(tag, "PS4 detected: ${device.name}")
                return device
            }
        }
        return null
    }

    private fun isPS4(device: InputDevice): Boolean {
        val name = device.name.lowercase()
        return name.contains("ps4") ||
                name.contains("dualshock") ||
                name.contains("playstation") ||
                name.contains("wireless controller")
    }

    fun handleKeyEvent(event: KeyEvent): String? {
        val mapped = mapButton(event.keyCode)
        if (mapped != null) {
            val actionName = if (event.action == KeyEvent.ACTION_DOWN) "DOWN" else "UP"
            val result = "PS4 ${buttonName(event.keyCode)} -> Xbox $mapped [$actionName]"
            Log.d(tag, result)
            return result
        }
        return null
    }

    fun handleMotionEvent(event: MotionEvent): String? {
        val axis = when (event.axis) {
            MotionEvent.AXIS_X -> "Left Stick X"
            MotionEvent.AXIS_Y -> "Left Stick Y"
            MotionEvent.AXIS_RX -> "Right Stick X"
            MotionEvent.AXIS_RY -> "Right Stick Y"
            MotionEvent.AXIS_Z -> "Left Trigger"
            MotionEvent.AXIS_RZ -> "Right Trigger"
            else -> "Axis ${event.axis}"
        }

        val value = event.getAxisValue(event.axis)
        if (value != 0f) {
            val result = "Motion $axis = $value"
            Log.d(tag, result)
            return result
        }
        return null
    }

    fun mapButton(ps4Code: Int): String? {
        return when (ps4Code) {
            96 -> "Y"      // Triangle
            97 -> "A"      // Cross
            98 -> "B"      // Circle
            99 -> "X"      // Square
            102 -> "LB"    // L1
            103 -> "RB"    // R1
            104 -> "Back"  // Share
            105 -> "Start" // Options
            106 -> "Guide" // PS button
            107 -> "LS"    // L3
            108 -> "RS"    // R3
            else -> null
        }
    }

    fun buttonName(ps4Code: Int): String {
        return when (ps4Code) {
            96 -> "Triangle"
            97 -> "Cross"
            98 -> "Circle"
            99 -> "Square"
            102 -> "L1"
            103 -> "R1"
            104 -> "Share"
            105 -> "Options"
            106 -> "PS"
            107 -> "L3"
            108 -> "R3"
            else -> "Unknown"
        }
    }
}
