package com.sparshchadha.expensetracker.feature.auth.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sparshchadha.expensetracker.utils.AppColors
import com.sparshchadha.expensetracker.utils.Dimensions

@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = phoneNumber,
        onValueChange = { newValue ->
            if (newValue != "$phoneNumber\n" && newValue.length <= 15) onPhoneNumberChange(newValue)
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
                        onPhoneNumberChange("")
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
                .size(Dimensions.mediumPadding())
                .padding(2.dp)
                .align(Alignment.Center)
                .clickable(onClick = onClick),
            tint = Color.DarkGray
        )
    }
}