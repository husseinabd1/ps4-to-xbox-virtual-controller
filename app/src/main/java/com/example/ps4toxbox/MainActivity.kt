package com.example.ps4toxbox

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ps4toxbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ps4Reader: PS4ControllerReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOpenAccessibility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        binding.buttonStartService.setOnClickListener {
            if (startGamepadService()) {
                binding.textStatus.text = "Service status: started"
            } else {
                binding.textStatus.text = "Service status: failed"
            }
        }

        ps4Reader = PS4ControllerReader(
            context = this,
            onButtonPressed = { mappedCode, action ->
                Log.d("MainActivity", "Mapped Xbox button: $mappedCode action=$action")
                VirtualGamepadManager.sendMappedButton(mappedCode, action)
            },
            onAxisChanged = { axis, value ->
                Log.d("MainActivity", "Axis $axis = $value")
            }
        )

        val found = ps4Reader.findPS4Controller()
        binding.textStatus.text = if (found) {
            "PS4 controller detected"
        } else {
            "No PS4 controller detected"
        }
    }

    private fun startGamepadService(): Boolean {
        return try {
            val intent = Intent(this, GamepadService::class.java)
            startService(intent)
            Log.d("MainActivity", "GamepadService started")
            true
        } catch (e: Exception) {
            Log.e("MainActivity", "Failed to start GamepadService", e)
            false
        }
    }

    companion object {
        init {
            System.loadLibrary("virtual_xbox")
        }
    }

    external fun createNativeVirtualXbox(): Boolean
}
