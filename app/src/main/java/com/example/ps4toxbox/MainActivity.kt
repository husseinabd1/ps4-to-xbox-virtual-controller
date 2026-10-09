package com.example.ps4toxbox

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ps4toxbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOpenAccessibility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        binding.buttonStartService.setOnClickListener {
            startGamepadService()
        }
    }

    private fun startGamepadService() {
        try {
            val intent = Intent(this, GamepadService::class.java)
            startService(intent)
            binding.textStatus.text = "Service status: started"
            Log.d("MainActivity", "GamepadService started")
        } catch (e: Exception) {
            binding.textStatus.text = "Service status: failed to start"
            Log.e("MainActivity", "Failed to start service", e)
        }
    }
}
