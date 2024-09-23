package com.sparshchadha.expensetracker.common.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import java.util.Date
import java.util.Locale

fun Context.vibrateDevice() {
    val vibrator: Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibrator = vibratorManager.defaultVibrator
    } else {
        vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }


    vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(
        this,
        message,
        duration
    ).show()
}

fun Long.convertMillisToDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS Z", Locale.getDefault())
    return formatter.format(Date(this))
}

fun Long.convertToHumanReadableDate(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return formatter.format(Date(this))
}

/**
* To be used when converting date from backend to show on frontend
* */
fun String.convertToHumanReadableDate(): String {
    return try {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS Z", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("dd/MMM/yyyy, hh:mm a", Locale.getDefault())
        val date = inputFormatter.parse(this)
        date?.let { outputFormatter.format(it) } ?: this
    } catch (e: Exception) {
        Utility.errorLog(e.message ?: "Unable to parse date")
        this // Return the original string if parsing fails
    }
}

fun String.convertStrMillisToHumanReadableDate(): String {
    return try {
        this.toLong().convertToHumanReadableDate()
    } catch (e: Exception) {
        Utility.errorLog(e.message ?: "Unable to parse date")
        this
    }
}
