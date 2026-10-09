package com.example.ps4toxbox

import android.view.KeyEvent

class ControllerMapper {

    fun mapPs4Button(ps4Code: Int): Int? {
        return when (ps4Code) {
            96 -> KeyEvent.KEYCODE_BUTTON_Y
            97 -> KeyEvent.KEYCODE_BUTTON_A
            98 -> KeyEvent.KEYCODE_BUTTON_B
            99 -> KeyEvent.KEYCODE_BUTTON_X
            102 -> KeyEvent.KEYCODE_BUTTON_L1
            103 -> KeyEvent.KEYCODE_BUTTON_R1
            104 -> KeyEvent.KEYCODE_BUTTON_SELECT
            105 -> KeyEvent.KEYCODE_BUTTON_START
            106 -> KeyEvent.KEYCODE_BUTTON_MODE
            107 -> KeyEvent.KEYCODE_BUTTON_THUMBL
            108 -> KeyEvent.KEYCODE_BUTTON_THUMBR
            else -> null
        }
    }
}
