package com.example.ps4toxbox

import android.view.KeyEvent

object XboxMapper {
    fun mapPS4Button(ps4Code: Int): String? {
        return when (ps4Code) {
            96 -> "Y"
            97 -> "A"
            98 -> "B"
            99 -> "X"
            102 -> "LB"
            103 -> "RB"
            104 -> "Back"
            105 -> "Start"
            106 -> "Guide"
            107 -> "LS"
            108 -> "RS"
            else -> null
        }
    }
}
