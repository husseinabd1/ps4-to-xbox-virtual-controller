package com.example.ps4toxbox

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.input.InputManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent

class GamepadMapperService : Service() {

    private val TAG = "GamepadMapperService"
    private lateinit var inputManager: InputManager
    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false
    private var deviceId: Int = -1

    override fun onCreate() {
        super.onCreate()
        inputManager = getSystemService(Context.INPUT_SERVICE) as InputManager
        Log.d(TAG, "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isRunning) {
            isRunning = true
            findAndMonitorPS4Device()
            Log.d(TAG, "Service started")
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun findAndMonitorPS4Device() {
        handler.post(object : Runnable {
            override fun run() {
                if (isRunning) {
                    val devices = inputManager.inputDeviceIds
                    for (id in devices) {
                        val device = inputManager.getInputDevice(id)
                        if (device != null && isPS4Controller(device)) {
                            deviceId = id
                            Log.d(TAG, "Found PS4: ${device.name}")
                            break
                        }
                    }
                    handler.postDelayed(this, 2000) // Check every 2 seconds
                }
            }
        })
    }

    private fun isPS4Controller(device: InputDevice): Boolean {
        val name = device.name.lowercase()
        return name.contains("ps4") || name.contains("dualshock") || name.contains("playstation")
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        Log.d(TAG, "Service destroyed")
    }
}
