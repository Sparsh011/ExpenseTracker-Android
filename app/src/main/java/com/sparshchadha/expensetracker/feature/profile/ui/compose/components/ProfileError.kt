package com.sparshchadha.expensetracker.feature.profile.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.ui.components.compose.Pulsating
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes

@Composable
fun ProfileError(
    error: String,
    errorMessage: String,
    canRetry: Boolean,
    onRetryProfileFetch: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(Dimensions.largePadding())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Pulsating (
            pulseFraction = 1.05f
        ){
            Box(
                modifier = Modifier
                    .padding(Dimensions.largePadding())
                    .clip(CircleShape)
                    .size(Dimensions.errorIconSize())
                    .align(Alignment.CenterHorizontally)
                    .background(AppColors.primaryPurple)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(Dimensions.errorIconSize())
                )
            }
        }


        Text(
            text = error,
            color = AppColors.errorRed,
            fontSize = FontSizes.extraLargeFontSize().value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = errorMessage,
            color = Color.DarkGray,
            fontSize = FontSizes.mediumFontSize().value.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        if (canRetry) {
            Button(
                onClick = {
                    onRetryProfileFetch()
                },
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.primaryColor
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_retry),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Dimensions.smallPadding()))
                    Text(text = "Retry", fontSize = FontSizes.mediumNonScaledFontSize())
                }
            }
        }
    }
}