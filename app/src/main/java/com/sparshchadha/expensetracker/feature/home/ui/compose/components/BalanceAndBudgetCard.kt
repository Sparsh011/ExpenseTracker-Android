package com.sparshchadha.expensetracker.feature.home.ui.compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes

@Composable
fun BalanceAndBudgetCard(
    cardHeight: Double,
    onClick: () -> Unit,
    expenseBudget: Int,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .height(cardHeight.dp)
            .fillMaxWidth()
            .padding(Dimensions.largePadding())
            .shadow(
                elevation = Dimensions.extraLargePadding(),
                shape = RoundedCornerShape(Dimensions.cornerRadius())
            ),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.primaryColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimensions.extraLargePadding()
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            RemainingBalance()

            Budget(expenseBudget)
        }

        BudgetAndBalanceProgress()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Primary Card",
                color = Color.LightGray,
                fontSize = FontSizes.mediumNonScaledFontSize(),
                modifier = Modifier.padding(
                    horizontal = Dimensions.mediumPadding(),
                )
            )

            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.padding(horizontal = Dimensions.largePadding())
            )
        }
    }
}

@Composable
private fun RemainingBalance() {
    Column {
        Text(
            text = "69,000 RS",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = FontSizes.largeNonScaledFontSize(),
            modifier = Modifier.padding(
                start = Dimensions.mediumPadding(),
                top = Dimensions.mediumPadding(),
                bottom = Dimensions.extraSmallPadding()
            )
        )

        Text(
            text = "Balance",
            color = Color.LightGray,
            fontSize = FontSizes.mediumNonScaledFontSize(),
            modifier = Modifier.padding(
                horizontal = Dimensions.mediumPadding(),
            )
        )
    }
}

@Composable
private fun Budget(
    budget: Int
) {
    Column {
        Text(
            text = if (budget != -1) budget.toString() else "Add budget",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = FontSizes.largeNonScaledFontSize(),
            modifier = Modifier.padding(
                end = Dimensions.mediumPadding(),
                top = Dimensions.mediumPadding(),
                bottom = Dimensions.extraSmallPadding()
            )
        )

        Text(
            text = "Budget",
            color = Color.LightGray,
            fontSize = FontSizes.mediumNonScaledFontSize(),
            modifier = Modifier.padding(
                horizontal = Dimensions.mediumPadding(),
            )
        )
    }
}

@Composable
private fun BudgetAndBalanceProgress() {
    var progress by rememberSaveable { mutableFloatStateOf(0F) }
    val progressAnimDuration = 1_000
    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(progressAnimDuration), label = "",
    )
    LaunchedEffect(key1 = Unit) {
        progress = 0.7f
    }

    val gradient = Brush.linearGradient(
        colors = listOf(AppColors.primaryGreen, AppColors.primaryPurple)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.largePadding(),
                vertical = Dimensions.largePadding()
            )
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = progressAnimation)
                .clip(RoundedCornerShape(2.dp))
                .background(gradient)
        )
    }
}