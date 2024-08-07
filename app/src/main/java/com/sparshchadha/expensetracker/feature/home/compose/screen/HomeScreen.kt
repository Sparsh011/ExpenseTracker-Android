package com.sparshchadha.expensetracker.feature.home.compose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.feature.home.compose.components.BalanceAndBudgetCard
import com.sparshchadha.expensetracker.feature.home.compose.components.CurrentDayExpenses
import com.sparshchadha.expensetracker.feature.home.compose.components.GreetingAndTopBarIcons
import com.sparshchadha.expensetracker.feature.home.compose.components.Top5TransactionsList
import com.sparshchadha.expensetracker.utils.AppColors
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes


private const val TAG = "HomeScreen"
@Composable
fun HomeScreen(
    navigateToNotificationsFragment: () -> Unit,
    navigateToProfileFragment: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Column(
        modifier = Modifier
            .background(AppColors.secondaryWhite)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = Dimensions.statusBarPadding())
    ) {
        GreetingAndTopBarIcons(
            navigateToNotificationsScreen = navigateToNotificationsFragment,
            navigateToProfileScreen = navigateToProfileFragment
        )

        BalanceAndBudgetCard(
            cardHeight = 0.3 * screenHeight,
            onClick = {

            }
        )

        ExpensesHeader()

        CurrentDayExpenses()

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Top5TransactionsList()

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Footer()
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