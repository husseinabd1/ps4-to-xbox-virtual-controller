package com.example.ps4toxbox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.ps4toxbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ps4Reader: PS4ControllerReader
    private lateinit var gamepadMonitor: GamepadMonitor
    private lateinit var eventLogger: EventLogger
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventLogger = EventLogger()
        eventLogger.setOnLogUpdated { logText ->
            binding.textEventLog.text = logText
        }

        // Initialize PS4 controller reader
        ps4Reader = PS4ControllerReader(
            context = this,
            onButtonPressed = { mappedCode, action ->
                val buttonName = getButtonName(mappedCode)
                val actionName = if (action == KeyEvent.ACTION_DOWN) "DOWN" else "UP"
                eventLogger.log("PS4", "Button: $buttonName ($actionName)")
                Log.d(TAG, "Mapped PS4 -> Xbox button: $mappedCode")
                VirtualGamepadManager.sendMappedButton(mappedCode, action)
            },
            onAxisChanged = { axis, value ->
                val axisName = getAxisName(axis)
                eventLogger.log("AXIS", "$axisName: %.2f".format(value))
            }
        )

        // Initialize gamepad monitor
        gamepadMonitor = GamepadMonitor(
            context = this,
            onConnected = { device ->
                eventLogger.log("DEVICE", "Connected: ${device.name}")
                binding.textDeviceInfo.text = "Device: ${device.name}\nID: ${device.id}"
            },
            onDisconnected = { device ->
                eventLogger.log("DEVICE", "Disconnected: ${device.name}")
            }
        )

        // Setup button listeners
        binding.buttonScanDevices.setOnClickListener {
            scanDevices()
        }

        binding.buttonOpenAccessibility.setOnClickListener {
            SystemUtils.openAccessibilitySettings(this)
        }

        binding.buttonStartService.setOnClickListener {
            startGamepadService()
        }

        binding.buttonCheckRoot.setOnClickListener {
            checkSystemCapabilities()
        }

        // Initial scan
        gamepadMonitor.scan()
        updateStatus()
    }

    private fun scanDevices() {
        gamepadMonitor.scan()
        eventLogger.log("SCAN", "Device scan completed")
        updateStatus()
    }

    private fun startGamepadService() {
        val isEnabled = SystemUtils.isAccessibilityServiceEnabled(this, GamepadService::class.java)
        if (isEnabled) {
            val intent = Intent(this, GamepadService::class.java)
            startService(intent)
            eventLogger.log("SERVICE", "GamepadService started")
            binding.textStatus.text = "Service: Running"
        } else {
            eventLogger.log("SERVICE", "Accessibility Service not enabled. Please enable it first.")
            binding.textStatus.text = "Service: Accessibility disabled"
        }
    }

    private fun checkSystemCapabilities() {
        val rooted = SystemUtils.isDeviceRooted()
        val uinputAvailable = SystemUtils.canAccessUinput()
        val androidVersion = SystemUtils.getAndroidVersion()
        val deviceName = SystemUtils.getDeviceName()

        val message = """Device: $deviceName
Android API: $androidVersion
Rooted: $rooted
uinput available: $uinputAvailable"""

        eventLogger.log("SYSTEM", message)
        binding.textStatus.text = if (rooted && uinputAvailable) {
            "Status: Full Xbox emulation available"
        } else {
            "Status: Fallback mode only"
        }
    }

    private fun updateStatus() {
        val hasPS4 = ps4Reader.findPS4Controller()
        binding.textStatus.text = if (hasPS4) {
            "Status: PS4 Controller Detected"
        } else {
            "Status: No PS4 Controller"
        }
    }

    private fun getButtonName(code: Int): String {
        return when (code) {
            KeyEvent.KEYCODE_BUTTON_A -> "A (Cross)"
            KeyEvent.KEYCODE_BUTTON_B -> "B (Circle)"
            KeyEvent.KEYCODE_BUTTON_X -> "X (Square)"
            KeyEvent.KEYCODE_BUTTON_Y -> "Y (Triangle)"
            KeyEvent.KEYCODE_BUTTON_L1 -> "LB (L1)"
            KeyEvent.KEYCODE_BUTTON_R1 -> "RB (R1)"
            KeyEvent.KEYCODE_BUTTON_SELECT -> "Back (Share)"
            KeyEvent.KEYCODE_BUTTON_START -> "Start (Options)"
            KeyEvent.KEYCODE_BUTTON_MODE -> "Guide (PS)"
            KeyEvent.KEYCODE_BUTTON_THUMBL -> "LS Click (L3)"
            KeyEvent.KEYCODE_BUTTON_THUMBR -> "RS Click (R3)"
            else -> "Unknown ($code)"
        }
    }

    private fun getAxisName(axis: Int): String {
        return when (axis) {
            MotionEvent.AXIS_X -> "Left Stick X"
            MotionEvent.AXIS_Y -> "Left Stick Y"
            MotionEvent.AXIS_Z -> "Left Trigger"
            MotionEvent.AXIS_RX -> "Right Stick X"
            MotionEvent.AXIS_RY -> "Right Stick Y"
            MotionEvent.AXIS_RZ -> "Right Trigger"
            else -> "Axis $axis"
        }
    }

    companion object {
        init {
            try {
                System.loadLibrary("virtual_xbox")
            } catch (e: UnsatisfiedLinkError) {
                Log.w("MainActivity", "Native library not loaded: ${e.message}")
            }
        }
    }

    external fun createNativeVirtualXbox(): Boolean
}
