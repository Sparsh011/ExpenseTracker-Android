package com.sparshchadha.expensetracker.feature.transactions.presentation.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.sparshchadha.expensetracker.common.ui.components.compose.ETTopBar
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.components.ExpenseCard

@Composable
fun TransactionsScreen(
    allExpenses: List<ExpenseEntity>,
    onExpenseClick: (Int) -> Unit
) {
    Column (
        modifier = Modifier.background(AppColors.primaryWhite)
            .fillMaxSize(),
    ) {
        ETTopBar(text = "Transactions History")

        Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

        // Add search bar here to search by date, name description or amount all

        LazyColumn (
            modifier = Modifier.fillMaxSize()
        ) {
            items(allExpenses) {
                ExpenseCard(
                    expense = it,
                    onExpenseItemClick = onExpenseClick
                )
            }
        }
    }
}

@Composable
private fun Header(
    text: String,
    icon: ImageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.secondaryWhite)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = FontSizes.largeNonScaledFontSize(),
            modifier = Modifier.weight(
                0.8f
            ),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            imageVector = icon,
            tint = Color.Black,
            modifier = Modifier.weight(0.2f),
            contentDescription = null
        )
    }
}