package com.sparshchadha.expensetracker.feature.auth.ui.compose.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.feature.auth.ui.compose.components.ChangeNumberAndCancelView
import com.sparshchadha.expensetracker.feature.auth.ui.compose.components.OTPInput
import com.sparshchadha.expensetracker.utils.AppColors
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes
import com.sparshchadha.expensetracker.utils.FontSizes.nonScaledSp
import kotlinx.coroutines.delay

@Composable
fun VerifyOtpScreen(
    providedPhoneNumber: String,
    onVerify: (String) -> Unit,
    onResend: () -> Unit,
    onChangeNumber: () -> Unit,
    onCancel: () -> Unit,
) {
    var timer by rememberSaveable {
        mutableIntStateOf(30)
    }

    var resendCount by rememberSaveable {
        mutableIntStateOf(1)
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            if (timer > 0) timer--
            delay(1000L)
        }
    }

    val context = LocalContext.current
    var otp by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ChangeNumberAndCancelView(
            providedPhoneNumber = providedPhoneNumber,
            onChangeNumber = onChangeNumber,
            onCancel = onCancel
        )

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        OTPInput(
            otpLength = 6,
            onOTPComplete = {
                Toast.makeText(context, "OTP $it", Toast.LENGTH_SHORT).show()
                otp = it
            }
        )

        Spacer(modifier = Modifier.height(Dimensions.extraLargePadding()))

        VerifyOTPButton {
            onVerify(otp)
        }

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        if (timer > 0) {
            Text(
                text = "Resend OTP after $timer seconds",
                color = Color.Black,
                fontSize = 16.nonScaledSp
            )
            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))
        }

        ResendOTPButton(
            timer = timer,
            onResend = onResend,
            updateResendCount = {
                resendCount += 1
                timer += 30 * resendCount
            }
        )

        Spacer(modifier = Modifier.height(Dimensions.extraLargePadding()))
    }
}


@Composable
private fun VerifyOTPButton(
    onVerify: () -> Unit,
) {
    Button(
        onClick = {
            onVerify()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.extraLargePadding()),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.primaryColor
        )
    ) {
        Text(text = "Verify OTP", color = Color.White)
    }
}


@Composable
private fun ResendOTPButton(
    timer: Int,
    onResend: () -> Unit,
    updateResendCount: () -> Unit,
) {
    Button(
        onClick = {
            if (timer == 0) {
                onResend()
                updateResendCount()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.extraLargePadding()),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (timer == 0) AppColors.primaryColor else Color.Gray
        )
    ) {
        Text(text = "Resend OTP", color = Color.White)
    }
}
