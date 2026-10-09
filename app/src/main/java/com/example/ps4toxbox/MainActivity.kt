package com.example.ps4toxbox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private lateinit var logger: EventLogger
    private var serviceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logger = EventLogger()

        val statusText = findViewById<TextView>(R.id.textStatus)
        val logText = findViewById<TextView>(R.id.textLog)
        val scanBtn = findViewById<Button>(R.id.buttonScan)
        val startBtn = findViewById<Button>(R.id.buttonStart)
        val stopBtn = findViewById<Button>(R.id.buttonStop)

        scanBtn.setOnClickListener {
            val device = PS4InputReader(this).scanForPS4()
            if (device != null) {
                val msg = "PS4 detected: ${device.name}"
                logger.log(msg)
                statusText.text = "Status: PS4 Found"
                logText.text = logger.getText()
                Log.d(tag, msg)
            } else {
                val msg = "No PS4 device found"
                logger.log(msg)
                statusText.text = "Status: No PS4"
                logText.text = logger.getText()
            }
        }

        startBtn.setOnClickListener {
            val intent = Intent(this, PS4Service::class.java)
            startService(intent)
            serviceRunning = true
            statusText.text = "Status: Service Running"
            logger.log("Service started")
            logText.text = logger.getText()
        }

        stopBtn.setOnClickListener {
            val intent = Intent(this, PS4Service::class.java)
            stopService(intent)
            serviceRunning = false
            statusText.text = "Status: Stopped"
            logger.log("Service stopped")
            logText.text = logger.getText()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val msg = PS4InputReader(this).handleKeyEvent(event)
        val logView = findViewById<TextView>(R.id.textLog)
        if (msg != null) {
            logger.log(msg)
            logView.text = logger.getText()
        }
        return super.onKeyDown(keyCode, event)
    }
}
