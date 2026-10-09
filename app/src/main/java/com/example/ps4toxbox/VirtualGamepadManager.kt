package com.example.ps4toxbox

import android.util.Log
import android.view.KeyEvent

object VirtualGamepadManager {

    fun sendMappedButton(mappedCode: Int, action: Int): Boolean {
        val label = when (action) {
            KeyEvent.ACTION_DOWN -> "DOWN"
            KeyEvent.ACTION_UP -> "UP"
            else -> "UNKNOWN"
        }

        Log.d("VirtualGamepadManager", "Emulated Xbox button: $mappedCode ($label)")

        // This is a fallback prototype only.
        // Real device injection requires root + /dev/uinput.
        return true
    }
}
