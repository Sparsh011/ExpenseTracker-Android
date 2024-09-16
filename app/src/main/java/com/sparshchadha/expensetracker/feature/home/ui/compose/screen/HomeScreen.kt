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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.feature.home.ui.compose.components.BalanceAndBudgetCard
import com.sparshchadha.expensetracker.feature.home.ui.compose.components.CurrentDayExpenses
import com.sparshchadha.expensetracker.feature.home.ui.compose.components.GreetingAndTopBarIcons
import com.sparshchadha.expensetracker.feature.home.ui.compose.components.Top5TransactionsList
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile

@Composable
fun HomeScreen(
    navigateToNotificationsFragment: () -> Unit,
    navigateToProfileFragment: () -> Unit,
    isNoTransactionsAnimShown: Boolean,
    expenseBudget: Int,
    userName: String,
    profileUri: String
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
                userName = userName
            )
        }

        item {
            BalanceAndBudgetCard(
                cardHeight = 0.3 * screenHeight,
                expenseBudget = expenseBudget,
                onClick = {

                }
            )
        }

        item {
            ExpensesHeader()
        }

        item {
            CurrentDayExpenses(
                isEmptyListAnimationShown = isNoTransactionsAnimShown,
            )
        }

        item {
            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))
        }

        item {
            Top5TransactionsList()
        }

        item {
            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))
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
private fun Footer() {
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