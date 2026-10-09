package com.example.ps4toxbox

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class GamepadService : AccessibilityService() {

    private val mapper = ControllerMapper()

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("GamepadService", "Accessibility service connected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Fallback only. Real Xbox emulation requires root + uinput.
    }

    override fun onInterrupt() {
        Log.d("GamepadService", "Interrupted")
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        val mapped = mapper.mapPs4Button(event.keyCode)
        if (mapped != null) {
            Log.d(
                "GamepadService",
                "Mapped PS4 key ${event.keyCode} -> Xbox key $mapped action=${event.action}"
            )
            return true
        }
        return super.onKeyEvent(event)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("GamepadService", "Service unbound")
        return super.onUnbind(intent)
    }
}
