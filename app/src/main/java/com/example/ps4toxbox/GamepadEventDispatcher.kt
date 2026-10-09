package com.example.ps4toxbox

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent

class GamepadEventDispatcher(private val accessibilityService: AccessibilityService?) {

    private val TAG = "GamepadDispatcher"

    fun dispatchKeyEvent(keyCode: Int, action: Int) {
        try {
            val keyEvent = if (action == KeyEvent.ACTION_DOWN) {
                KeyEvent(KeyEvent.ACTION_DOWN, keyCode)
            } else {
                KeyEvent(KeyEvent.ACTION_UP, keyCode)
            }

            // Try accessibility service first
            if (accessibilityService != null) {
                val handled = accessibilityService.onKeyEvent(keyEvent)
                if (handled) {
                    Log.d(TAG, "Key event $keyCode dispatched via AccessibilityService")
                    return
                }
            }

            // Fallback: log only (requires root+uinput for real injection)
            Log.d(TAG, "Key event $keyCode action=$action (logged only - not injected)")
        } catch (e: Exception) {
            Log.e(TAG, "Error dispatching key event", e)
        }
    }

    fun dispatchMotionEvent(axisCode: Int, value: Float) {
        try {
            Log.d(TAG, "Motion axis $axisCode value=$value (logged only)")
        } catch (e: Exception) {
            Log.e(TAG, "Error dispatching motion event", e)
        }
    }
}
