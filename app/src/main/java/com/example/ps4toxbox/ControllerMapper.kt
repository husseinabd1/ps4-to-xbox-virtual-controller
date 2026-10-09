package com.example.ps4toxbox

import android.view.KeyEvent

class ControllerMapper {

    fun mapPs4Button(ps4Code: Int): Int? {
        return when (ps4Code) {
            97 -> KeyEvent.KEYCODE_BUTTON_A      // Cross -> A
            98 -> KeyEvent.KEYCODE_BUTTON_B      // Circle -> B
            99 -> KeyEvent.KEYCODE_BUTTON_X      // Square -> X
            100 -> KeyEvent.KEYCODE_BUTTON_Y     // Triangle -> Y
            102 -> KeyEvent.KEYCODE_BUTTON_L1    // L1 -> LB
            103 -> KeyEvent.KEYCODE_BUTTON_R1    // R1 -> RB
            104 -> KeyEvent.KEYCODE_BUTTON_SELECT // Share -> Back
            105 -> KeyEvent.KEYCODE_BUTTON_START  // Options -> Start
            106 -> KeyEvent.KEYCODE_BUTTON_MODE   // PS -> Guide
            else -> null
        }
    }
}
