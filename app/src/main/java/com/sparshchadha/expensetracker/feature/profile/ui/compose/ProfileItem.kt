package com.sparshchadha.expensetracker.feature.profile.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileItem(
    trailingIcon: ImageVector,
    trailingIconTint: Color = Color(red = 41, green = 53, blue = 94, alpha = 255),
    trailingText: String,
    trailingTextColor: Color = Color.Black,
    leadingText: String = "",
    leadingTextColor: Color = Color.Black,
    leadingIcon: ImageVector = Icons.Default.KeyboardArrowRight,
    leadingIconTint: Color = Color.DarkGray,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = trailingIcon,
            contentDescription = null,
            tint = trailingIconTint,
            modifier = Modifier
                .padding(end = Dimensions.extraSmallPadding())
                .size(Dimensions.sliderIconSize())
                .padding(Dimensions.extraSmallPadding())
        )

        Text(
            text = trailingText,
            color = trailingTextColor,
            fontSize = FontSizes.mediumNonScaledFontSize(),
            modifier = Modifier.weight(0.5f)
        )

        Text(
            text = leadingText,
            color = leadingTextColor,
            fontSize = FontSizes.mediumNonScaledFontSize(),
            modifier = Modifier
                .weight(0.3f)
                .basicMarquee(5),
            textAlign = TextAlign.End
        )

        Icon(
            imageVector = leadingIcon,
            contentDescription = null,
            tint = leadingIconTint,
            modifier = Modifier.weight(0.1f)
        )
    }
}