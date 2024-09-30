package com.sparshchadha.expensetracker.feature.expense.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.ui.components.compose.ETTopBar
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.common.utils.formatAmount
import kotlin.math.abs

@Composable
fun ExpenseSettingsScreen(
    currentExpenseBudget: Double,
    onChangeBudget: (Int) -> Unit,
    onBackPress: () -> Unit,
) {
    var newExpenseBudget by rememberSaveable {
        mutableStateOf(currentExpenseBudget.toString())
    }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        ETTopBar(
            text = "Expense Settings",
            onBackPress = onBackPress,
            isBackEnabled = true,
            modifier = Modifier
                .statusBarsPadding()
                .height(Dimensions.topBarHeight())
                .fillMaxWidth()
        )

        Text(
            buildAnnotatedString {
                append("Current expense budget ")
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(currentExpenseBudget.formatAmount())
                }
            },
            color = Color.Black,
            modifier = Modifier
                .padding(
                    horizontal = Dimensions.largePadding(),
                    vertical = Dimensions.mediumPadding()
                ),
            fontSize = FontSizes.mediumNonScaledFontSize()
        )

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "New Expense Budget",
                fontSize = FontSizes.mediumNonScaledFontSize(),
                modifier = Modifier.padding(start = Dimensions.largePadding())
            )

            TextField(
                value = newExpenseBudget,
                onValueChange = {
                    if (it.length > 9) return@TextField
                    newExpenseBudget = it
                },
                modifier = Modifier
                    .padding(horizontal = Dimensions.mediumPadding())
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = AppColors.primaryColor,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            newExpenseBudget = ""
                        },
                        tint = Color.Black
                    )
                },
                maxLines = 1,
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_rupee),
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.mediumPadding())
                    )
                }
            )
        }

        val newBudget = newExpenseBudget.toDoubleOrNull() ?: 0.0

        val percentageChange = if (currentExpenseBudget.toDouble() != 0.0) {
            ((abs(newBudget - currentExpenseBudget) / currentExpenseBudget) * 100)
        } else {
            0.0
        }

        val comparisonText = if (newBudget > currentExpenseBudget) {
            " greater than current budget."
        } else {
            " less than current budget"
        }

        val percentageColor = when {
            percentageChange > 1.0 && newBudget > currentExpenseBudget -> AppColors.primaryGreen
            percentageChange > 1.0 && newBudget < currentExpenseBudget -> Color.Red
            else -> Color.Black
        }

        Text(
            buildAnnotatedString {
                append("New expense budget is ")
                withStyle(
                    style = SpanStyle(
                        color = percentageColor,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("${"%.2f".format(percentageChange)}%")
                }
                append(comparisonText)
            },
            color = Color.Black,
            modifier = Modifier
                .padding(
                    horizontal = Dimensions.largePadding(),
                    vertical = Dimensions.mediumPadding()
                ),
            fontSize = FontSizes.mediumNonScaledFontSize()
        )

        Button(
            onClick = {
                if (newExpenseBudget.toDoubleOrNull() == null) return@Button
                onChangeBudget(newExpenseBudget.toDouble().toInt())
            }, modifier = Modifier
                .padding(
                    horizontal = Dimensions.extraLargePadding(),
                    vertical = Dimensions.smallPadding()
                )
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.primaryColor
            )
        ) {
            Text(text = "Update Expense Budget", color = Color.White)
        }
    }
}