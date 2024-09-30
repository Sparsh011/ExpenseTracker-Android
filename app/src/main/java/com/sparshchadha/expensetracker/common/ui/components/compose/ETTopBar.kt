package com.sparshchadha.expensetracker.common.ui.components.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.common.utils.FontSizes

@Composable
fun ETTopBar(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    isBackEnabled: Boolean = false,
    onBackPress: () -> Unit = {},
    backButtonColor: Color = Color.Black,
    textColor: Color = Color.Black,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isBackEnabled) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .weight(0.2f)
                    .clickable {
                        onBackPress()
                    },
                tint = backButtonColor
            )
        }

        Text(
            text = text,
            color = textColor,
            fontSize = FontSizes.largeNonScaledFontSize().value.sp,
            fontWeight = fontWeight,
            modifier = Modifier.weight(0.8f)
        )
    }
}