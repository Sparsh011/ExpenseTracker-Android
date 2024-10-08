package com.sparshchadha.expensetracker.feature.auth.presentation.ui.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.feature.auth.presentation.ui.compose.components.PhoneNumberTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    continueWithPhoneAuth: (String) -> Unit,
    startGoogleSignIn: () -> Unit,
    onLoginSkip: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    ScreenContent(
        screenWidth = screenWidth,
        continueWithPhoneAuth = continueWithPhoneAuth,
        startGoogleSignIn = startGoogleSignIn,
        onLoginSkip = onLoginSkip
    )
}

@Composable
private fun ScreenContent(
    screenWidth: Int,
    continueWithPhoneAuth: (String) -> Unit,
    startGoogleSignIn: () -> Unit,
    onLoginSkip: () -> Unit,
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
            painter = painterResource(id = R.drawable.img_expense_tracker_app),
            contentDescription = null,
            modifier = Modifier
                .width(screenWidth.dp)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = Dimensions.cornerRadius(),
                        bottomStart = Dimensions.cornerRadius()
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
            },
        )

        SendOTPButton {
            continueWithPhoneAuth("+91-$phoneNumber")
        }

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Text(text = "Or", color = Color.Black, fontSize = FontSizes.smallFontSize().value.sp)

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        LoginWithGoogleButton {
            startGoogleSignIn()
        }

        Spacer(modifier = Modifier.height(Dimensions.extraLargePadding()))

        Text(
            text = "Do it later",
            fontSize = FontSizes.mediumNonScaledFontSize(),
            color = Color.Black,
            modifier = Modifier.clickable {
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
    startGoogleSignIn: () -> Unit,
) {
    Button(
        onClick = {
            startGoogleSignIn()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.secondaryWhite
        ),
        shape = RoundedCornerShape(Dimensions.extraSmallPadding()) // Intentionally not used cornerRadius to simulate the real button
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_google),
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