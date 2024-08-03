package com.sparshchadha.expensetracker.feature.auth.ui.compose

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.utils.AppColors
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyOtpScreen(
    providedPhoneNumber: String,
    onDismiss: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        sheetState = bottomSheetState
    ) {
        Column {
            ChangeNumberView(providedPhoneNumber = providedPhoneNumber, onChange = onDismiss)

            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

            OTPInput(
                otpLength = 6,
                onOTPComplete = {
                    Toast.makeText(context, "OTP $it", Toast.LENGTH_SHORT).show()
                }
            )

            Spacer(modifier = Modifier.height(Dimensions.extraLargePadding()))
        }
    }
}

@Composable
private fun OTPInput(
    otpLength: Int = 6,
    onOTPComplete: (String) -> Unit
) {
    val otp = remember { mutableStateListOf("").apply {
        for (i in 0 until otpLength) {
            this.add("")
        }
    } }
    val focusRequesters = List(otpLength) { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimensions.extraSmallPadding()),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.smallPadding())
    ) {
        for (i in 0 until otpLength) {
            OutlinedTextField(
                value = otp[i],
                onValueChange = { value ->
                    if (value.length <= 1) {
                        otp[i] = value
                        if (value.isNotEmpty() && i < otpLength - 1) {
                            focusRequesters[i + 1].requestFocus()
                        } else if (value.isBlank() && i > 0) {
                            focusRequesters[i - 1].requestFocus()
                        }

                        if (otp.all { it.isNotEmpty() }) {
                            onOTPComplete(otp.joinToString(""))
                        }
                    }
                },
                singleLine = true,
                textStyle = TextStyle.Default.copy(textAlign = TextAlign.Center, fontSize = FontSizes.extraSmallFontSize().value.sp),
                modifier = Modifier
                    .focusRequester(focusRequesters[i])
                    .weight(1f)
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Backspace) {
                            if (i > 0 && otp[i].isBlank()) {
                                focusRequesters[i - 1].requestFocus()
                            }
                        } else if (otp[i].isNotBlank() && i < otpLength - 1) {
                            focusRequesters[i + 1].requestFocus()
                        }
                        false
                    },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                )
            )
        }
    }
}


@Composable
private fun ChangeNumberView(providedPhoneNumber: String, onChange: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Not $providedPhoneNumber?"
        )

        Spacer(modifier = Modifier.width(Dimensions.mediumPadding()))

        Text(
            text = "Change Number",
            modifier = Modifier.clickable { onChange() },
            color = AppColors.primaryPurple
        )
    }
}