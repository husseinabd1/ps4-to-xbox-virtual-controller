package com.example.ps4toxbox

import android.content.Context
import android.hardware.input.InputManager
import android.util.Log
import android.view.InputDevice
import android.view.KeyEvent

class PS4Reader(context: Context) {
    private val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
    private val TAG = "PS4Reader"
    
    fun scanForPS4(): InputDevice? {
        val devices = inputManager.inputDeviceIds
        for (id in devices) {
            val device = inputManager.getInputDevice(id)
            if (device != null && isPS4(device)) {
                Log.d(TAG, "Found PS4: ${device.name}")
                return device
            }
        }
        return null
    }
    
    private fun isPS4(device: InputDevice): Boolean {
        val name = device.name.lowercase()
        return name.contains("ps4") || 
               name.contains("dualshock") || 
               name.contains("wireless controller") ||
               name.contains("playstation")
    }
    
    fun mapButton(ps4Code: Int): Int? {
        return when (ps4Code) {
            96 -> KeyEvent.KEYCODE_BUTTON_Y      // Triangle → Y
            97 -> KeyEvent.KEYCODE_BUTTON_A      // Cross → A
            98 -> KeyEvent.KEYCODE_BUTTON_B      // Circle → B
            99 -> KeyEvent.KEYCODE_BUTTON_X      // Square → X
            102 -> KeyEvent.KEYCODE_BUTTON_L1    // L1 → LB
            103 -> KeyEvent.KEYCODE_BUTTON_R1    // R1 → RB
            104 -> KeyEvent.KEYCODE_BUTTON_SELECT // Share → Back
            105 -> KeyEvent.KEYCODE_BUTTON_START  // Options → Start
            106 -> KeyEvent.KEYCODE_BUTTON_MODE   // PS → Guide
            107 -> KeyEvent.KEYCODE_BUTTON_THUMBL // L3
            108 -> KeyEvent.KEYCODE_BUTTON_THUMBR // R3
            else -> null
        }
    }
}
