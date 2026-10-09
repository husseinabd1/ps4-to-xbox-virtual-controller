package com.example.ps4toxbox

import android.view.KeyEvent

object ButtonMap {
    
    /**
     * PS4 to Xbox button mapping
     */
    val ps4ToXboxMap = mapOf(
        96 to KeyEvent.KEYCODE_BUTTON_Y,      // PS4 Triangle -> Xbox Y
        97 to KeyEvent.KEYCODE_BUTTON_A,      // PS4 Cross -> Xbox A
        98 to KeyEvent.KEYCODE_BUTTON_B,      // PS4 Circle -> Xbox B
        99 to KeyEvent.KEYCODE_BUTTON_X,      // PS4 Square -> Xbox X
        102 to KeyEvent.KEYCODE_BUTTON_L1,    // PS4 L1 -> Xbox LB
        103 to KeyEvent.KEYCODE_BUTTON_R1,    // PS4 R1 -> Xbox RB
        104 to KeyEvent.KEYCODE_BUTTON_SELECT,// PS4 Share -> Xbox Back
        105 to KeyEvent.KEYCODE_BUTTON_START, // PS4 Options -> Xbox Start
        106 to KeyEvent.KEYCODE_BUTTON_MODE,  // PS4 PS -> Xbox Guide
        107 to KeyEvent.KEYCODE_BUTTON_THUMBL,// PS4 L3 -> Xbox LS click
        108 to KeyEvent.KEYCODE_BUTTON_THUMBR // PS4 R3 -> Xbox RS click
    )

    fun mapPS4Button(ps4Code: Int): Int? {
        return ps4ToXboxMap[ps4Code]
    }

    fun getPS4ButtonName(code: Int): String {
        return when (code) {
            96 -> "Triangle"
            97 -> "Cross"
            98 -> "Circle"
            99 -> "Square"
            102 -> "L1"
            103 -> "R1"
            104 -> "Share"
            105 -> "Options"
            106 -> "PS"
            107 -> "L3"
            108 -> "R3"
            else -> "Unknown"
        }
    }

    fun getXboxButtonName(code: Int): String {
        return when (code) {
            KeyEvent.KEYCODE_BUTTON_Y -> "Y"
            KeyEvent.KEYCODE_BUTTON_A -> "A"
            KeyEvent.KEYCODE_BUTTON_B -> "B"
            KeyEvent.KEYCODE_BUTTON_X -> "X"
            KeyEvent.KEYCODE_BUTTON_L1 -> "LB"
            KeyEvent.KEYCODE_BUTTON_R1 -> "RB"
            KeyEvent.KEYCODE_BUTTON_SELECT -> "Back"
            KeyEvent.KEYCODE_BUTTON_START -> "Start"
            KeyEvent.KEYCODE_BUTTON_MODE -> "Guide"
            KeyEvent.KEYCODE_BUTTON_THUMBL -> "LS Click"
            KeyEvent.KEYCODE_BUTTON_THUMBR -> "RS Click"
            else -> "Unknown"
        }
    }
}
