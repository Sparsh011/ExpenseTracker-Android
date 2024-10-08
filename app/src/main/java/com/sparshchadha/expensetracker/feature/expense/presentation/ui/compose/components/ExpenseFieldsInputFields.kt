package com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import java.util.Locale

@Composable
fun ExpenseFieldInputFields(
    title: String,
    description: String,
    amount: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    descriptionTextFieldHeight: Dp,
) {
    TitleAndDescriptionTextField(
        value = title,
        onValueChange = onTitleChange,
        labelText = "Title",
        limit = 25
    )

    TitleAndDescriptionTextField(
        value = description,
        onValueChange = onDescriptionChange,
        labelText = "Description",
        modifier = Modifier.height(descriptionTextFieldHeight),
        limit = 250
    )

    AmountTextField(
        value = amount,
        onValueChange = onAmountChange,
        labelText = "Amount in ₹",
        keyboardType = KeyboardType.Number
    )
}

@Composable
private fun TitleAndDescriptionTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    limit: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    BasicTextField(
        value = value,
        onValueChange = { input ->
            if (input.length > limit) return@BasicTextField
            onValueChange(input.replace(Regex("""(\r\n)|\n"""), "").replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.UK) else it.toString().replace(Regex("""(\r\n)|\n"""), "") })
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.extraSmallPadding()),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, capitalization = KeyboardCapitalization.Sentences),
        textStyle = TextStyle(
            fontSize = FontSizes.mediumNonScaledFontSize(),
            color = Color.DarkGray
        ),
        cursorBrush = SolidColor(Color.DarkGray),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                    .background(AppColors.secondaryWhite)
                    .fillMaxWidth()
                    .padding(Dimensions.mediumPadding())
            ) {
                if (value.isBlank()) {
                    Text(
                        text = labelText,
                        fontSize = FontSizes.mediumNonScaledFontSize(),
                        color = Color.LightGray,
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
private fun AmountTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    BasicTextField(
        value = value,
        onValueChange = {
            if (it.isBlank()) {
                onValueChange("")
                return@BasicTextField
            }
            if (it.toDoubleOrNull() == null) return@BasicTextField
            if (it.contains('.') && it.substring(
                    it.indexOf('.'),
                    it.length
                ).length > 3
            ) return@BasicTextField
            if (it.length > 8) return@BasicTextField
            onValueChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.extraSmallPadding()),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = TextStyle(
            fontSize = FontSizes.mediumNonScaledFontSize(),
            color = Color.DarkGray
        ),
        cursorBrush = SolidColor(Color.DarkGray),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                    .background(AppColors.secondaryWhite)
                    .fillMaxWidth()
                    .padding(Dimensions.mediumPadding())
            ) {
                if (value.isBlank()) {
                    Text(
                        text = labelText,
                        fontSize = FontSizes.mediumNonScaledFontSize(),
                        color = Color.LightGray,
                    )
                }
                innerTextField()
            }
        }
    )
}