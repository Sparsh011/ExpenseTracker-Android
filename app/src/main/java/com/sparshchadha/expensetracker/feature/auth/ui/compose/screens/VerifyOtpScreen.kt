package com.sparshchadha.expensetracker.feature.auth.ui.compose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
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
    onCancel: () -> Unit,
) {
    var timer by rememberSaveable {
        mutableIntStateOf(60)
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

    ScreenContent(
        onCancel = onCancel,
        providedPhoneNumber = providedPhoneNumber,
        onResend = onResend,
        timer = timer,
        onVerify = onVerify,
        resendCount = resendCount,
        onUpdateResendCount = { newResendCount ->
            timer += 30 * newResendCount
            resendCount = newResendCount
        },
    )
}

@Composable
private fun ScreenContent(
    onCancel: () -> Unit,
    providedPhoneNumber: String,
    onResend: () -> Unit,
    timer: Int,
    onVerify: (String) -> Unit,
    resendCount: Int,
    onUpdateResendCount: (Int) -> Unit,
) {
    var otp by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimensions.mediumPadding()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .weight(0.1f)
                    .clickable {
                        onCancel()
                    }
            )

            Text(
                text = "Verify Phone Number",
                color = Color.Black,
                fontSize = FontSizes.largeFontSize().value.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(0.9f)
                    .wrapContentHeight()
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.extraLargePadding()))

        Text(
            text = "Code has been sent to $providedPhoneNumber",
            color = Color.Black,
            fontSize = FontSizes.mediumNonScaledFontSize().value.sp,
            modifier = Modifier
                .wrapContentHeight()
        )

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        OTPInput(
            otpLength = 6,
            onOTPComplete = {
                otp = it
                onVerify(otp)
            },
        )

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        if (timer > 0) {
            Text(
                text = "Didn't get OTP? \nResend OTP after $timer seconds",
                color = Color.Black,
                fontSize = 16.nonScaledSp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))
        }

        ResendOTPButton(
            timer = timer,
            onResend = onResend,
            updateResendCount = {
                onUpdateResendCount(resendCount + 1)
            }
        )

        Spacer(modifier = Modifier.height(Dimensions.largePadding()))

        VerifyOTPButton {
            onVerify(otp)
        }
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
            containerColor = AppColors.primaryPurple
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
    Text(
        text = "Resend OTP",
        color = if (timer == 0) AppColors.primaryPurple else Color.LightGray,
        fontSize = FontSizes.mediumNonScaledFontSize(),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.clickable {
            if (timer == 0) {
                onResend()
                updateResendCount()
            }
        }
    )
}