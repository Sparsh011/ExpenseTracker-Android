package com.sparshchadha.expensetracker.feature.auth.ui.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.ui.compose.components.PhoneNumberTextField
import com.sparshchadha.expensetracker.utils.AppColors
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes

@Composable
fun LoginScreen(
    navigateToVerifyOtpScreen: (Pair<String, String>) -> Unit,
    navigateToHomeScreen: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.expense_tracker_app_icon),
            contentDescription = null,
            modifier = Modifier
                .width(screenWidth.dp)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = Dimensions.largePadding(),
                        bottomStart = Dimensions.largePadding()
                    )
                ),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(Dimensions.largePadding()))

        PhoneNumberTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.largePadding()),
            phoneNumber = phoneNumber,
            onPhoneNumberChange = {
                phoneNumber = it
            }
        )

        SendOTPButton {
            navigateToVerifyOtpScreen(Pair(phoneNumber, "+91"))
        }

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Text(text = "Or", color = Color.Black, fontSize = FontSizes.smallFontSize().value.sp)

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Button(onClick = {
            navigateToHomeScreen()
        }) {
            Text(text = "Placeholder button for login with google button")
        }
    }
}

@Composable
private fun SendOTPButton(
    onSendOtp: () -> Unit,
) {
    Button(
        onClick = onSendOtp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.extraLargePadding()),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.primaryColor
        )
    ) {
        Text(text = "Send OTP", color = Color.White)
    }
}