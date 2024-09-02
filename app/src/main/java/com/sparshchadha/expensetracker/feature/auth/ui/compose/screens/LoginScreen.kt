package com.sparshchadha.expensetracker.feature.auth.ui.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.ui.compose.components.PhoneNumberTextField
import com.sparshchadha.expensetracker.utils.AppColors
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes

@Composable
fun LoginScreen(
    continueWithPhoneAuth: (String) -> Unit,
    showLoader: Boolean,
    startGoogleSignIn: () -> Unit,
    onLoginSkip: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Box(modifier = Modifier.fillMaxSize()) {
        ScreenContent(
            screenWidth = screenWidth,
            continueWithPhoneAuth = continueWithPhoneAuth,
            startGoogleSignIn = startGoogleSignIn,
            isLoaderShowing = showLoader,
            onLoginSkip = onLoginSkip
        )

        if (showLoader) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color(248, 248, 248, 170)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(Dimensions.sliderContainerSize()),
                    color = AppColors.primaryPurple,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun ScreenContent(
    screenWidth: Int,
    continueWithPhoneAuth: (String) -> Unit,
    startGoogleSignIn: () -> Unit,
    isLoaderShowing: Boolean,
    onLoginSkip: () -> Unit
) {
    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
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
                if (isLoaderShowing) return@PhoneNumberTextField
                phoneNumber = it
            },
            isEnabled = !isLoaderShowing
        )

        SendOTPButton {
            if (isLoaderShowing) return@SendOTPButton
            continueWithPhoneAuth("+91$phoneNumber")
        }

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Text(text = "Or", color = Color.Black, fontSize = FontSizes.smallFontSize().value.sp)

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        LoginWithGoogleButton(isLoaderShowing = isLoaderShowing) {
            startGoogleSignIn()
        }

        Spacer(modifier = Modifier.height(Dimensions.extraLargePadding()))

        Text(
            text = "Do it later",
            fontSize = FontSizes.mediumNonScaledFontSize(),
            color = Color.Black,
            modifier = Modifier.clickable {
                if (isLoaderShowing) return@clickable
                onLoginSkip()
            },
            fontStyle = FontStyle.Italic
        )
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
        Text(
            text = "Continue With Phone",
            color = Color.White,
            fontSize = FontSizes.mediumNonScaledFontSize()
        )
    }
}

@Composable
private fun LoginWithGoogleButton(
    isLoaderShowing: Boolean,
    startGoogleSignIn: () -> Unit
) {
    Button(
        onClick = {
            if (isLoaderShowing) return@Button
            startGoogleSignIn()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.secondaryWhite
        ),
        shape = RoundedCornerShape(Dimensions.smallPadding())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.google_icon),
                contentDescription = null,
                modifier = Modifier.size(Dimensions.mediumPadding())
            )

            Spacer(modifier = Modifier.width(Dimensions.mediumPadding()))

            Text(
                text = "Login With Google",
                color = Color.Black,
                fontSize = FontSizes.mediumNonScaledFontSize()
            )
        }
    }
}