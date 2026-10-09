package com.example.ps4toxbox

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
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
        // This service is used as a fallback only. It cannot fully emulate a real Xbox peripheral
        // without root + uinput. We log key events here for diagnostics and testing.
    }

    override fun onInterrupt() {
        Log.d("GamepadService", "Accessibility service interrupted")
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        val mappedCode = mapper.mapPs4Button(event.keyCode)
        if (mappedCode != null) {
            Log.d(
                "GamepadService",
                "PS4 key ${event.keyCode} mapped to Xbox key $mappedCode, action=${event.action}"
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
