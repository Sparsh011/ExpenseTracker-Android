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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.common.utils.formatAmount
import okhttp3.internal.format

@Composable
fun BalanceAndBudgetCard(
    cardHeight: Double,
    onClick: () -> Unit,
    expenseBudget: Double,
    amountSpentInLast30Days: Double,
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
            RemainingBalance(amountSpentInLast30Days)

            Budget(expenseBudget)
        }

        BudgetAndBalanceProgress(amountSpent = amountSpentInLast30Days, expenseBudget = expenseBudget)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = (expenseBudget - amountSpentInLast30Days).formatAmount() + " balance remaining.",
                color = Color.White,
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
private fun RemainingBalance(balance: Double) {
    Column {
        Text(
            text = balance.formatAmount(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = FontSizes.largeNonScaledFontSize(),
            modifier = Modifier.padding(
                start = Dimensions.mediumPadding(),
                top = Dimensions.mediumPadding(),
                bottom = Dimensions.extraSmallPadding()
            ),
            textAlign = TextAlign.Start
        )

        Text(
            text = "Spent",
            color = Color.LightGray,
            fontSize = FontSizes.mediumNonScaledFontSize(),
            modifier = Modifier.padding(
                horizontal = Dimensions.mediumPadding(),
            ),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun Budget(
    budget: Double
) {
    val formattedBudget = if (budget != -1.0) budget.formatAmount() else "Add budget"

    Column {
        Text(
            text = formattedBudget,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = FontSizes.largeNonScaledFontSize(),
            modifier = Modifier.padding(
                end = Dimensions.mediumPadding(),
                top = Dimensions.mediumPadding(),
                bottom = Dimensions.extraSmallPadding()
            ),
            textAlign = TextAlign.End
        )

        Text(
            text = "Budget",
            color = Color.LightGray,
            fontSize = FontSizes.mediumNonScaledFontSize(),
            modifier = Modifier.padding(
                horizontal = Dimensions.mediumPadding(),
            ),
            textAlign = TextAlign.End
        )
    }
}


@Composable
private fun BudgetAndBalanceProgress(
    amountSpent: Double,
    expenseBudget: Double
) {
    val targetProgress = if (expenseBudget > 0) (amountSpent / expenseBudget).toFloat() else 0f

    val progressAnimation by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(1_000),
        label = ""
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
                .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                .background(AppColors.primaryPurple)
        )
    }
}