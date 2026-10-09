package com.example.ps4toxbox

import android.view.KeyEvent

object ButtonMap {
    private val map = mapOf(
        96 to KeyEvent.KEYCODE_BUTTON_Y,
        97 to KeyEvent.KEYCODE_BUTTON_A,
        98 to KeyEvent.KEYCODE_BUTTON_B,
        99 to KeyEvent.KEYCODE_BUTTON_X,
        102 to KeyEvent.KEYCODE_BUTTON_L1,
        103 to KeyEvent.KEYCODE_BUTTON_R1,
        104 to KeyEvent.KEYCODE_BUTTON_SELECT,
        105 to KeyEvent.KEYCODE_BUTTON_START,
        106 to KeyEvent.KEYCODE_BUTTON_MODE,
        107 to KeyEvent.KEYCODE_BUTTON_THUMBL,
        108 to KeyEvent.KEYCODE_BUTTON_THUMBR
    )

    fun mapPS4Button(ps4Code: Int): Int? = map[ps4Code]
}
