package com.sparshchadha.expensetracker.feature.profile.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes

@Composable
fun ProfileFetchError(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.smallPadding()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_something_went_wrong),
            contentDescription = "Error Icon",
            tint = AppColors.errorRed,
            modifier = Modifier
                .size(Dimensions.errorIconSize())
                .padding(bottom = Dimensions.smallPadding())
        )

        Text(
            text = "Unable to fetch your profile",
            fontSize = FontSizes.largeNonScaledFontSize(),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = Dimensions.smallPadding())
        )

        Text(
            text = "Please check your connection and try again.",
            fontSize = FontSizes.mediumNonScaledFontSize(),
            color = Color.LightGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(Dimensions.mediumPadding())
        )

        Button(
            onClick = onRetryClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.secondaryWhite
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.largePadding())
                .shadow(
                    elevation = Dimensions.smallPadding() -  Dimensions . extraSmallPadding(),
                    shape = RoundedCornerShape(Dimensions.cornerRadius())
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_retry),
                    contentDescription = "Retry",
                    tint = AppColors.errorRed
                )
                Spacer(modifier = Modifier.width(Dimensions.smallPadding()))
                Text(
                    text = "Retry",
                    color = AppColors.errorRed,
                    fontSize = FontSizes.mediumNonScaledFontSize()
                )
            }
        }
    }
}
