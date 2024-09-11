package com.sparshchadha.expensetracker.feature.auth.ui.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes

@Composable
fun OTPInput(
    otpLength: Int = 6,
    onOTPComplete: (String) -> Unit,
) {
    var otp by rememberSaveable {
        mutableStateOf("")
    }

    BasicTextField(
        value = otp,
        onValueChange = {
            if (!it.isDigitsOnly()) return@BasicTextField

            if (it.length == otpLength) {
                otp = it
                onOTPComplete(otp)
                return@BasicTextField
            } else if (it.length < otpLength) {
                otp = it
            }
        },
        maxLines = 1,
        textStyle = TextStyle.Default.copy(
            textAlign = TextAlign.Center,
            fontSize = FontSizes.mediumFontSize().value.sp
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        decorationBox = {
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(Dimensions.smallPadding())
            ) {
                for (i in 0..< 6) {
                    val char = if (i >= otp.length) "" else otp[i].toString()

                    Text(
                        text = char,
                        color = Color.Black,
                        fontSize = FontSizes.mediumNonScaledFontSize(),
                        modifier = Modifier
                            .width(Dimensions.otpBoxWidth())
                            .border(
                                width = if (i == otp.length) 2.dp else 1.dp,
                                color = if (i == otp.length) Color.DarkGray else Color.LightGray,
                                shape = RoundedCornerShape(Dimensions.cornerRadius())
                            )
                            .padding(
                                Dimensions.smallPadding()
                            ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(Dimensions.smallPadding()))
                }
            }
        }
    )
}