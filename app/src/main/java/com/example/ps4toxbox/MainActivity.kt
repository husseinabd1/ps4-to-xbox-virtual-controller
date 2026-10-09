package com.example.ps4toxbox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var serviceRunning = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val statusText = findViewById<TextView>(R.id.textStatus)
        val startBtn = findViewById<Button>(R.id.buttonStart)
        val stopBtn = findViewById<Button>(R.id.buttonStop)
        val scanBtn = findViewById<Button>(R.id.buttonScan)
        
        startBtn.setOnClickListener {
            if (!serviceRunning) {
                startService(Intent(this, PS4Service::class.java))
                serviceRunning = true
                statusText.text = "✓ Service Running - PS4 buttons are mapped to Xbox"
                Toast.makeText(this, "Service started!", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Service started")
            }
        }
        
        stopBtn.setOnClickListener {
            if (serviceRunning) {
                stopService(Intent(this, PS4Service::class.java))
                serviceRunning = false
                statusText.text = "✗ Service Stopped"
                Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Service stopped")
            }
        }
        
        scanBtn.setOnClickListener {
            val ps4 = PS4Reader(this).scanForPS4()
            if (ps4 != null) {
                statusText.text = "✓ PS4 Found: ${ps4.name}"
                Toast.makeText(this, "PS4 Controller Detected!", Toast.LENGTH_SHORT).show()
            } else {
                statusText.text = "✗ No PS4 Controller Found"
                Toast.makeText(this, "Please connect PS4 controller", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
