package com.sparshchadha.expensetracker.feature.auth.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.utils.AppColors
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes

@Composable
fun LoginScreen() {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }
    var showVerifyOtpBottomSheet by rememberSaveable {
        mutableStateOf(false)
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

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.largePadding()),
            value = phoneNumber,
            onValueChange = { newValue ->
                if (newValue != "$phoneNumber\n" && newValue.length <= 15) phoneNumber = newValue
            },
            leadingIcon = {
                LeadingIcon(
                    onClick = {

                    }
                )
            }, trailingIcon = {
                if (phoneNumber.isNotBlank()) {
                    TrailingIcon(
                        onClick = {
                            phoneNumber = ""
                        }
                    )
                }
            },
            label = {
                Text(text = "Phone Number")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedPlaceholderColor = Color.Gray,
                focusedBorderColor = AppColors.primaryPurple
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Button(
            onClick = {
                showVerifyOtpBottomSheet = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.extraLargePadding()),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.primaryColor
            )
        ) {
            Text(text = "Send OTP", color = Color.White)
        }

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Text(text = "Or", color = Color.Black, fontSize = FontSizes.smallFontSize().value.sp)

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Button(onClick = {

        }) {
            Text(text = "Placeholder button for login with google button")
        }

        if (showVerifyOtpBottomSheet) {
            VerifyOtpScreen(
                providedPhoneNumber = "+91 $phoneNumber",
                onDismiss = {
                    showVerifyOtpBottomSheet = false
                }
            )
        }
    }
}

@Composable
private fun LeadingIcon(
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(text = "+91")
        Spacer(modifier = Modifier.width(Dimensions.smallPadding()))
        VerticalDivider(modifier = Modifier.height(Dimensions.largePadding()))
    }
}

@Composable
private fun TrailingIcon(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.LightGray)
    ) {
        Icon(
            imageVector = Icons.Filled.Clear,
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
                .align(
                    Alignment.Center
                )
                .clickable(onClick = onClick),
            tint = Color.DarkGray
        )
    }
}