package com.sparshchadha.expensetracker.common.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import java.text.NumberFormat
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

/**
 * Formats the given amount (double) to include commas in it for better visualization and prefixes Rupee symbol
 * */
fun Double.formatAmount(language: String = "en", country: String = "IN", prefixRupeeSymbol: Boolean = true): String {
    val formatter = NumberFormat.getNumberInstance(Locale(language, country))
    return if (prefixRupeeSymbol) "₹" + formatter.format(this) else formatter.format(this)
}

/**
 * Formats the given Date string to ISO string for DB operations.
 * */
fun String.convertToISOFormat(): String {
    val inputFormat = java.text.SimpleDateFormat("dd MM yyyy", Locale.getDefault())
    val outputFormat = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val date = inputFormat.parse(this)

    return outputFormat.format(date)
}


/**
 * Returns the corresponding month string to month index (1 based indexing)
 * */
fun Int.toMonthString(): String {
    return when (this) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> "Invalid"
    }
}


