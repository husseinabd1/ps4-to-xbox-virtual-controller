package com.example.ps4toxbox

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

class SystemUtils {
    companion object {
        private val TAG = "SystemUtils"

        fun isAccessibilityServiceEnabled(context: Context, serviceClass: Class<*>): Boolean {
            val accessibilityManager =
                context.getSystemService(Context.ACCESSIBILITY_SERVICE) as android.view.accessibility.AccessibilityManager
            val enabledServices =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            val serviceName = "${context.packageName}/${serviceClass.name}"
            return enabledServices != null && enabledServices.contains(serviceName)
        }

        fun openAccessibilitySettings(activity: Activity) {
            try {
                activity.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            } catch (e: Exception) {
                Log.e(TAG, "Failed to open accessibility settings", e)
            }
        }

        fun isDeviceRooted(): Boolean {
            return try {
                val result = Runtime.getRuntime().exec("which su").waitFor()
                result == 0
            } catch (e: Exception) {
                false
            }
        }

        fun canAccessUinput(): Boolean {
            return try {
                val runtime = Runtime.getRuntime()
                val process = runtime.exec("test -c /dev/uinput && echo 1")
                val reader = BufferedReader(InputStreamReader(process.inputStream))
                val output = reader.readLine()
                reader.close()
                process.waitFor()
                output == "1"
            } catch (e: Exception) {
                false
            }
        }

        fun getAndroidVersion(): Int = Build.VERSION.SDK_INT

        fun getDeviceName(): String = Build.DEVICE
    }
}
