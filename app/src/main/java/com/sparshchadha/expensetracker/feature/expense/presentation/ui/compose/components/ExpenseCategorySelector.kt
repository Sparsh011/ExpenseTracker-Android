package com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.core.domain.ExpenseCategories
import com.sparshchadha.expensetracker.feature.expense.presentation.ExpenseCategoryMapper

@Composable
fun ExpenseCategorySelector(
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                .background(AppColors.secondaryWhite)
                .padding(Dimensions.smallPadding())
        ) {
            Text(
                text = selectedCategory.ifBlank { "Select expense category" },
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(
                        start = Dimensions.smallPadding(),
                        end = Dimensions.mediumPadding()
                    )
                    .weight(0.8f),
                fontSize = FontSizes.mediumNonScaledFontSize(),
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.weight(0.2f)
            )
        }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryPopup(
    onCategorySelected: (ExpenseCategories) -> Unit,
    onDismiss: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        containerColor = AppColors.primaryWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val categories = ExpenseCategories.entries.toTypedArray()
            val rows = categories.size / 3 + if (categories.size % 3 > 0) 1 else 0

            repeat(rows) { rowIndex ->
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (columnIndex in 0 until 3) {
                        val categoryIndex = rowIndex * 3 + columnIndex
                        if (categoryIndex < categories.size) {
                            val category = categories[categoryIndex]
                            CategoryItem(
                                modifier = Modifier.weight(0.33f),
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
    Column(
        modifier = modifier
            .clickable { onClick() }
            .padding(Dimensions.mediumPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = ExpenseCategoryMapper.getIconForCategory(category)),
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(Dimensions.sliderIconSize())
        )

        Text(
            text = category.displayName,
            color = Color.Black,
            fontSize = FontSizes.smallNonScaledFontSize(),
            textAlign = TextAlign.Center,
        )
    }

}
