package com.example.ps4toxbox

import android.content.Context
import android.util.Log

class EventLogger(private val maxLines: Int = 50) {
    private val TAG = "EventLogger"
    private val logs = mutableListOf<String>()
    private var onLogUpdated: ((String) -> Unit)? = null

    fun setOnLogUpdated(callback: (String) -> Unit) {
        onLogUpdated = callback
    }

    fun log(tag: String, message: String) {
        val timestamp = System.currentTimeMillis()
        val logEntry = "[$tag] $message"
        logs.add(0, logEntry)

        if (logs.size > maxLines) {
            logs.removeAt(logs.size - 1)
        }

        Log.d(TAG, logEntry)
        onLogUpdated?.invoke(getFormattedLogs())
    }

    fun getFormattedLogs(): String {
        return if (logs.isEmpty()) {
            "No events yet"
        } else {
            logs.joinToString("\n")
        }
    }

    fun clear() {
        logs.clear()
        onLogUpdated?.invoke("Cleared")
    }
}
