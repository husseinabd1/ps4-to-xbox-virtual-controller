package com.example.ps4toxbox

class EventLogger {
    private val entries = mutableListOf<String>()

    fun log(message: String) {
        val time = System.currentTimeMillis()
        entries.add(0, "[$time] $message")
        if (entries.size > 50) {
            entries.removeAt(entries.size - 1)
        }
    }

    fun getText(): String {
        return if (entries.isEmpty()) {
            "Waiting for input..."
        } else {
            entries.joinToString("\n")
        }
    }
}
