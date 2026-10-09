package com.example.ps4toxbox

import android.content.Context
import android.hardware.input.InputManager
import android.util.Log
import android.view.InputDevice

class GamepadMonitor(
    private val context: Context,
    private val onConnected: (InputDevice) -> Unit,
    private val onDisconnected: (InputDevice) -> Unit
) {

    private val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
    private val connected = mutableSetOf<Int>()

    fun scan() {
        val ids = inputManager.inputDeviceIds
        for (id in ids) {
            val device = inputManager.getInputDevice(id) ?: continue
            if (isGamepad(device) && !connected.contains(id)) {
                connected.add(id)
                onConnected(device)
                Log.d("GamepadMonitor", "Connected: ${device.name} (id=$id)")
            }
        }
    }

    private fun isGamepad(device: InputDevice): Boolean {
        val name = device.name.lowercase()
        return name.contains("ps4") || name.contains("dualshock") || name.contains("playstation") ||
            name.contains("controller") || (device.sources and InputDevice.SOURCE_GAMEPAD != 0)
    }
}
