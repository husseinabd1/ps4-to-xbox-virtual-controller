package com.example.ps4toxbox

import android.view.KeyEvent

object VirtualGamepadManager {
    fun sendMappedButton(ps4Code: Int, action: Int): Boolean {
        val mapped = ControllerMapper().mapPs4Button(ps4Code)
        if (mapped == null) return false

        // This is intentionally a no-root fallback placeholder.
        // In a real rooted device, this would be sent via uinput /dev/uinput.
        val eventAction = when (action) {
            KeyEvent.ACTION_DOWN -> "DOWN"
            KeyEvent.ACTION_UP -> "UP"
            else -> "UNKNOWN"
        }

        android.util.Log.d(
            "VirtualGamepadManager",
            "Mapped PS4 button $ps4Code -> Xbox key $mapped ($eventAction)"
        )
        return true
    }
}
