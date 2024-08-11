package com.sparshchadha.expensetracker.feature.home.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes
import java.util.Calendar

@Composable
fun GreetingAndTopBarIcons(
    navigateToNotificationsScreen: () -> Unit,
    navigateToProfileScreen: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = getGreetingBasedOnTime(),
            fontSize = FontSizes.extraLargeNonScaledFontSize(),
            color = Color.Black,
            modifier = Modifier
                .weight(0.8f)
                .padding(Dimensions.mediumPadding()),
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier
                .padding(end = Dimensions.mediumPadding())
                .size(Dimensions.homeScreenTopBarIconSize())
                .clickable { navigateToProfileScreen() }
        )

        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier
                .padding(end = Dimensions.mediumPadding())
                .size(Dimensions.homeScreenTopBarIconSize())
                .clickable { navigateToNotificationsScreen() }
        )
    }
}


private fun getGreetingBasedOnTime(): String {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    return when (hour) {
        in 0..11 -> "Good Morning!"
        in 12..17 -> "Good Afternoon!"
        else -> "Good Evening!"
    }
}