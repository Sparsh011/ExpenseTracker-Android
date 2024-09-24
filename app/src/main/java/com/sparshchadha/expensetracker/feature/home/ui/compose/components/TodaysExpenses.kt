package com.sparshchadha.expensetracker.feature.home.ui.compose.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity

@Composable
fun CurrentDayExpenses(
    expenses: List<ExpenseEntity>
) {
    Text(
        text = "Today's Transactions",
        color = Color.DarkGray,
        modifier = Modifier.padding(
            start = Dimensions.largePadding(),
            end = Dimensions.mediumPadding()
        ),
        fontSize = FontSizes.mediumFontSize().value.sp
    )

    for (expense in expenses) {
        ExpenseCard(expense)
    }
}

@Composable
private fun NoTransactionsComposable(
    isEmptyListAnimationShown: Boolean,
) {

    var noTransactionsFontSize by remember {
        mutableFloatStateOf(0f)
    }

    LaunchedEffect(key1 = Unit) {
        noTransactionsFontSize = 24f
    }

    Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!isEmptyListAnimationShown) {
            Text(
                text = "No Transaction Today!",
                color = Color.Black,
                fontSize = noTransactionsFontSize.sp,
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
        } else {
            Text(
                text = "No Transaction Today!",
                color = Color.Black,
                fontSize = noTransactionsFontSize.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.empty_box_svg),
            contentDescription = null,
            tint = AppColors.primaryColor,
            modifier = Modifier.size(Dimensions.largeIconSize())
        )
    }
}
