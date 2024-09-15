package com.sparshchadha.expensetracker.common.utils

import android.util.Log
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
}