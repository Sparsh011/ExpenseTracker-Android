package com.sparshchadha.expensetracker.feature.expense.presentation.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.sparshchadha.expensetracker.common.utils.Dimensions

@Composable
fun ExpenseFieldInputFields(
    title: String,
    description: String,
    amount: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
) {
    ExpenseFieldTextField(value = title, onValueChange = onTitleChange, labelText = "Title")

    ExpenseFieldTextField(
        value = description,
        onValueChange = onDescriptionChange,
        labelText = "Description"
    )

    ExpenseFieldTextField(
        value = amount,
        onValueChange = onAmountChange,
        labelText = "Amount",
        keyboardType = KeyboardType.Number
    )
}

@Composable
private fun ExpenseFieldTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = labelText, color = Color.Black) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.mediumPadding(), vertical = Dimensions.extraSmallPadding()),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}