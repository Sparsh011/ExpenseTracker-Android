package com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.common.utils.convert24HourTimeTo12HourTime
import com.sparshchadha.expensetracker.common.utils.convertISODateToUIDate
import com.sparshchadha.expensetracker.common.utils.convertToHumanReadableDate
import com.sparshchadha.expensetracker.common.utils.formatAmount
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.ExpenseCategoryMapper

@Composable
fun ExpenseCard(expense: ExpenseEntity, onExpenseItemClick: (Int) -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(
                horizontal = Dimensions.mediumPadding(),
                vertical = Dimensions.extraSmallPadding()
            ),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.primaryWhite
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = Dimensions.largePadding()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = Dimensions.smallPadding())
                    .clip(CircleShape)
                    .size(Dimensions.extraLargePadding())
                    .background(AppColors.secondaryWhite)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = ExpenseCategoryMapper.getIconForCategory(expense.category)),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier.weight(0.60f)
                    .clickable { onExpenseItemClick(expense.expenseId ?: -1) }
            ) {
                Text(
                    text = expense.title,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(
                        start = Dimensions.largePadding(),
                        end = Dimensions.mediumPadding(),
                        bottom = Dimensions.extraSmallPadding()
                    ),
                    fontSize = FontSizes.mediumNonScaledFontSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    Text(
                        text = expense.createdOnDate.convertISODateToUIDate(),
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(
                                start = Dimensions.largePadding(),
                            )
                            .basicMarquee(2),
                        fontSize = FontSizes.smallNonScaledFontSize(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = expense.createdAtTime.convert24HourTimeTo12HourTime(),
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(
                                start = Dimensions.extraSmallPadding(),
                                end = Dimensions.mediumPadding()
                            )
                            .basicMarquee(2),
                        fontSize = FontSizes.smallNonScaledFontSize(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }


            }

            Text(
                text =  expense.amount.formatAmount(),
                color = AppColors.errorRed,
                fontSize = FontSizes.mediumNonScaledFontSize(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(0.2f),
                textAlign = TextAlign.End
            )

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = AppColors.errorRed,
                modifier = Modifier.clickable {
                    onDelete()
                }
            )

        }
    }
}