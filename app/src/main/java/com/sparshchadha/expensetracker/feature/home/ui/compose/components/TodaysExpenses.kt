package com.sparshchadha.expensetracker.feature.home.ui.compose.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components.ExpenseCard

@Composable
fun ExpenseListHandler(
    expenses: List<ExpenseEntity>,
    onExpenseItemClick: (Int) -> Unit,
) {
    for (expense in expenses) {
        ExpenseCard(expense, onExpenseItemClick)
    }
}

@Composable
fun NoTransactions(text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = FontSizes.largeNonScaledFontSize(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = StiffnessMediumLow
                    )
                )
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_empty_box),
            contentDescription = null,
            tint = AppColors.primaryColor,
            modifier = Modifier.size(Dimensions.largeIconSize())
        )
    }

}
