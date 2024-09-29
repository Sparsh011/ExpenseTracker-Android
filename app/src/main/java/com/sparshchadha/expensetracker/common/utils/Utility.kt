package com.sparshchadha.expensetracker.common.utils

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.text.isDigitsOnly

object Utility {
    private const val TAG = "BerryFinTag"
    fun errorLog(string: String) {
        Log.e(TAG, "Berry.Fin error log: $string")
    }

    fun debugLog(str: String) {
        Log.d(TAG, "Berry.Fin debugLog: $str")
    }

    fun isOtpValid(otp: String): Boolean {
        if (otp.isBlank()) return false
        if (otp.trim().length != 6) return false
        if (!otp.isDigitsOnly()) return false
        return true
    }

    fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
        this.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }
}