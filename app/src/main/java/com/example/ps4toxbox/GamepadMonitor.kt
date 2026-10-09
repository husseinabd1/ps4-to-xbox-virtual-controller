package com.example.ps4toxbox

import android.content.Context
import android.hardware.input.InputManager
import android.util.Log
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent

interface GamepadMonitorListener {
    fun onControllerConnected(device: InputDevice)
    fun onControllerDisconnected(device: InputDevice)
    fun onButtonEvent(deviceId: Int, keyCode: Int, action: Int)
    fun onAxisEvent(deviceId: Int, axisCode: Int, value: Float)
}

class GamepadMonitor(
    private val context: Context,
    private val listener: GamepadMonitorListener
) {

    private val TAG = "GamepadMonitor"
    private val inputManager = context.getSystemService(Context.INPUT_SERVICE) as InputManager
    private val connectedDevices = mutableSetOf<Int>()

    fun start() {
        Log.d(TAG, "Starting gamepad monitor")
        scanConnectedDevices()
    }

    fun stop() {
        Log.d(TAG, "Stopping gamepad monitor")
        connectedDevices.clear()
    }

    fun scanConnectedDevices() {
        val devices = inputManager.inputDeviceIds
        for (deviceId in devices) {
            val device = inputManager.getInputDevice(deviceId)
            if (device != null && isGamepad(device)) {
                if (!connectedDevices.contains(deviceId)) {
                    connectedDevices.add(deviceId)
                    listener.onControllerConnected(device)
                    Log.d(TAG, "Controller connected: ${device.name} (ID: $deviceId)")
                }
            }
        }
    }

    private fun isGamepad(device: InputDevice): Boolean {
        val sources = device.sources
        return (
            (sources and InputDevice.TOOL_TYPE_UNKNOWN != 0) ||
            (sources and InputDevice.TOOL_GENERIC_2 != 0)
        )
    }
}
