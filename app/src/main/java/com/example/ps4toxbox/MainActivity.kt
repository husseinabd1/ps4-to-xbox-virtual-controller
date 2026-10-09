package com.example.ps4toxbox

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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

        val textStatus = findViewById<TextView>(R.id.textStatus)
        val btnStartService = findViewById<Button>(R.id.buttonStartService)
        val btnStopService = findViewById<Button>(R.id.buttonStopService)
        val btnSettings = findViewById<Button>(R.id.buttonSettings)

        btnStartService.setOnClickListener {
            if (!serviceRunning) {
                val intent = Intent(this, GamepadMapperService::class.java)
                startService(intent)
                serviceRunning = true
                textStatus.text = "Status: Service Running"
                Toast.makeText(this, "Service started - connect PS4 and play!", Toast.LENGTH_SHORT)
                    .show()
                Log.d(TAG, "Service started")
            }
        }

        btnStopService.setOnClickListener {
            if (serviceRunning) {
                val intent = Intent(this, GamepadMapperService::class.java)
                stopService(intent)
                serviceRunning = false
                textStatus.text = "Status: Service Stopped"
                Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Service stopped")
            }
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
    }
}
