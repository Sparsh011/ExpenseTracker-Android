package com.sparshchadha.expensetracker.feature.transactions.presentation.ui.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.sparshchadha.expensetracker.common.ui.components.compose.ETTopBar
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.common.utils.convertISODateToUIDate
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components.ExpenseCard
import com.sparshchadha.expensetracker.feature.home.ui.compose.components.NoTransactions
import com.sparshchadha.expensetracker.feature.transactions.presentation.ui.compose.components.TransactionSearchBar

@Composable
fun TransactionsScreen(
    allExpenses: List<ExpenseEntity>,
    onExpenseClick: (Int) -> Unit,
    onSearch: (String) -> Unit,
    onDelete: (ExpenseEntity) -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Column (
        modifier = Modifier
            .background(AppColors.secondaryWhite)
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

        TransactionSearchBar(
            searchQuery = searchQuery,
            onSearch = {
                onSearch(searchQuery)
            },
            onSearchQueryChange = { newSearchQuery ->
                searchQuery = newSearchQuery
                onSearch(searchQuery)
            },
            focusManager = focusManager,
            focusRequester = focusRequester,
            onCloseClicked = {
                focusManager.clearFocus()
            }
        )

        if (allExpenses.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(allExpenses.size) { index ->
                    if (index == 0) {
                        Header(allExpenses[index].createdOnDate) { }
                    } else if (index > 0) {
                        if (allExpenses[index].createdOnDate != allExpenses[index - 1].createdOnDate) {
                            Header(allExpenses[index].createdOnDate) { }
                        }
                    }
                    Spacer(modifier = Modifier.height(Dimensions.smallPadding()))
                    ExpenseCard(
                        expense = allExpenses[index],
                        onExpenseItemClick = onExpenseClick,
                        onDelete = {
                            onDelete(allExpenses[index])
                        }
                    )
                }
            }
        } else {
            NoTransactions(text = "Could not find any transactions")
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
            .padding(Dimensions.smallPadding())
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text.convertISODateToUIDate(),
            color = Color.Black,
            fontSize = FontSizes.largeNonScaledFontSize(),
            modifier = Modifier.weight(
                0.8f
            ),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

//        Icon(
//            imageVector = icon,
//            tint = Color.Black,
//            modifier = Modifier.weight(0.2f),
//            contentDescription = null
//        )
    }
}