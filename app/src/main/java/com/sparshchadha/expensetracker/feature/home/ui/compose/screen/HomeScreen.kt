package com.sparshchadha.expensetracker.feature.home.ui.compose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.home.ui.compose.components.BalanceAndBudgetCard
import com.sparshchadha.expensetracker.feature.home.ui.compose.components.ExpenseListHandler
import com.sparshchadha.expensetracker.feature.home.ui.compose.components.GreetingAndTopBarIcons
import com.sparshchadha.expensetracker.feature.home.ui.compose.components.NoTransactions

@Composable
fun HomeScreen(
    navigateToNotificationsFragment: () -> Unit,
    navigateToProfileFragment: () -> Unit,
    expenseBudget: Double,
    userName: String,
    profileUri: String,
    allExpenses: List<ExpenseEntity>,
    onExpenseItemClick: (Int) -> Unit,
    amountSpentInLast30Days: Double,
    top5TransactionsByAmountSpent: SnapshotStateList<ExpenseEntity>,
    navigateToExpenseSettingsScreen: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .background(AppColors.secondaryWhite)
            .fillMaxSize()
            .padding(top = Dimensions.statusBarPadding()),
        state = listState
    ) {
        item {
            GreetingAndTopBarIcons(
                navigateToNotificationsScreen = navigateToNotificationsFragment,
                navigateToProfileScreen = navigateToProfileFragment,
                userName = userName,
                profileUri = profileUri
            )
        }

        item {
            BalanceAndBudgetCard(
                cardHeight = 0.3 * screenHeight,
                expenseBudget = expenseBudget,
                amountSpentInLast30Days = amountSpentInLast30Days,
                onClick = navigateToExpenseSettingsScreen
            )
        }

        item {
            ExpensesHeader()
        }

        item {
            SubHeading(text = "Today's Transactions")
            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))
        }

        if (allExpenses.isNotEmpty()) {
            item {
                ExpenseListHandler(
                    expenses = allExpenses,
                    onExpenseItemClick = onExpenseItemClick
                )
            }
        } else {
            item {
                NoTransactions(
                    text = "No Transaction Today"
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))
        }

        item {
            SubHeading(text = "Top 5 Expenses of Last 30 Days")
            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))
        }

        if (top5TransactionsByAmountSpent.isNotEmpty()) {
            item {
                ExpenseListHandler(
                    expenses = top5TransactionsByAmountSpent,
                    onExpenseItemClick = onExpenseItemClick
                )
            }
        } else {
            item {
                NoTransactions(
                    text = "You haven't logged any transaction in the last 30 days."
                )
            }
        }

        item {
            Footer()
        }
    }
}

@Composable
private fun ExpensesHeader() {
    Text(
        text = "Expenses",
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(
            horizontal = Dimensions.largePadding(),
            vertical = Dimensions.smallPadding()
        ),
        fontSize = FontSizes.extraLargeFontSize().value.sp
    )
}

@Composable
fun Footer() {
    Text(
        text = "Track, Save & Grow.",
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(
            horizontal = Dimensions.largePadding(),
        ),
        fontSize = FontSizes.extraLargeFontSize().value.sp
    )

    Text(
        text = "Manage all your expenses at one place and grow together with billions of people using Sparsh's Expense Tracker",
        color = Color.Black,
        modifier = Modifier.padding(
            horizontal = Dimensions.largePadding(),
            vertical = Dimensions.smallPadding()
        ),
        fontSize = FontSizes.mediumFontSize().value.sp
    )

    Text(
        text = "v1.0.0",
        color = Color.Gray,
        modifier = Modifier
            .padding(
                horizontal = Dimensions.largePadding(),
                vertical = Dimensions.smallPadding()
            )
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = FontSizes.mediumNonScaledFontSize().value.sp
    )
}

@Composable
private fun SubHeading(text: String) {
    Text(
        text = text,
        color = Color.DarkGray,
        modifier = Modifier.padding(
            start = Dimensions.largePadding(),
            end = Dimensions.mediumPadding()
        ),
        fontSize = FontSizes.mediumFontSize().value.sp
    )
}