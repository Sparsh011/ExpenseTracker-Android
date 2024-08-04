package com.sparshchadha.expensetracker.feature.home.compose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.feature.home.compose.components.BalanceAndBudgetCard
import com.sparshchadha.expensetracker.feature.home.compose.components.GreetingAndTopBarIcons
import com.sparshchadha.expensetracker.feature.home.compose.components.Top5TransactionsList
import com.sparshchadha.expensetracker.utils.AppColors
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes

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

        Box(
            contentAlignment = Alignment.Center
        ) {
            Column {
                Top5TransactionsList()
            }
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