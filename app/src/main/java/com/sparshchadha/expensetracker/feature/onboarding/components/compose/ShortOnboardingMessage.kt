package com.sparshchadha.expensetracker.feature.onboarding.components.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes

@Composable
fun ShortOnboardingMessage() {
    Text(
        text = "Manage All Expenses At One Place",
        color = Color.White,
        modifier = Modifier
            .padding(
                horizontal = Dimensions.largePadding()
            ),
        fontSize = FontSizes.extraLargeFontSize().value.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}