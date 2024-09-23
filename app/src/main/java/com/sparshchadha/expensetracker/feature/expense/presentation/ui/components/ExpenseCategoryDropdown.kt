package com.sparshchadha.expensetracker.feature.expense.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.core.domain.ExpenseCategories

@Composable
fun ExpenseCategoryPopup(
    selectedCategory: String,
    onItemSelect: (String) -> Unit,
) {

    var showCategoriesPopup by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showCategoriesPopup = !showCategoriesPopup
            }
    ) {
        Text(
            text = selectedCategory.ifBlank { "Select expense category" },
            color = Color.DarkGray,
            modifier = Modifier
                .padding(
                    start = Dimensions.largePadding(),
                    end = Dimensions.mediumPadding()
                ),
            fontSize = FontSizes.mediumFontSize().value.sp
        )

        if (showCategoriesPopup) {
            CategoryPopup(
                onCategorySelected = {
                    onItemSelect(it.displayName)
                },
                onDismiss = {
                    showCategoriesPopup = false
                }
            )
        }
    }
}

@Composable
private fun CategoryPopup(
    onCategorySelected: (ExpenseCategories) -> Unit,
    onDismiss: () -> Unit,
) {
    Popup(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val categories = ExpenseCategories.entries.toTypedArray()
            val rows = categories.size / 3 + if (categories.size % 3 > 0) 1 else 0

            repeat(rows) { rowIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppColors.secondaryWhite),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (columnIndex in 0 until 3) {
                        val categoryIndex = rowIndex * 3 + columnIndex
                        if (categoryIndex < categories.size) {
                            val category = categories[categoryIndex]
                            CategoryItem(
                                modifier = Modifier.weight(1f),
                                category
                            ) {
                                onCategorySelected(category)
                                onDismiss()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryItem(modifier: Modifier, category: ExpenseCategories, onClick: () -> Unit) {
    Text(
        text = category.displayName,
        modifier = modifier
            .clickable { onClick() }
            .padding(Dimensions.mediumPadding()),
        color = Color.Black,
        fontSize = FontSizes.smallNonScaledFontSize(),
        textAlign = TextAlign.Center
    )
}
