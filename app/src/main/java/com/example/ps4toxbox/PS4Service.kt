package com.example.ps4toxbox

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent

class PS4Service : Service() {
    private val tag = "PS4Service"
    private val reader = PS4InputReader(this)
    private val logger = EventLogger()

    override fun onCreate() {
        super.onCreate()
        Log.d(tag, "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val found = reader.scanForPS4()
        if (found != null) {
            logger.log("Device detected: ${found.name}")
            Log.d(tag, "PS4 connected: ${found.name}")
        } else {
            logger.log("No PS4 device found")
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    fun logKeyEvent(event: KeyEvent): String? {
        val result = reader.handleKeyEvent(event)
        if (result != null) {
            logger.log(result)
        }
        return result
    }

    fun getLogs(): String = logger.getText()
}
