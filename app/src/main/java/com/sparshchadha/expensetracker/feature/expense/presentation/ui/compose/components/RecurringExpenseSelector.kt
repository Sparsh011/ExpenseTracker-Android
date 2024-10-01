package com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes

@Composable
fun RecurringExpenseSelector(
    isRecurring: Boolean,
    onRecurringStateChange: (Boolean) -> Unit,
    recurAfterDays: String,
    onRecurAfterDaysChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimensions.cornerRadius()))
            .background(AppColors.secondaryWhite)
            .padding(Dimensions.smallPadding())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isRecurring) "Recur after ${recurAfterDays.ifBlank { 0 }} days" else "Should recur?",
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(
                        start = Dimensions.smallPadding(),
                        end = Dimensions.mediumPadding()
                    )
                    .weight(0.6f),
                fontSize = FontSizes.mediumNonScaledFontSize(),
            )

            Spacer(modifier = Modifier.width(Dimensions.smallPadding()))

            Switch(
                checked = isRecurring,
                onCheckedChange = { onRecurringStateChange(!isRecurring) },
                colors = SwitchDefaults.colors(checkedTrackColor = AppColors.primaryColor),
                modifier = Modifier.weight(0.4f)
            )
        }

        AnimatedVisibility(visible = isRecurring) {
            BasicTextField(
                value = recurAfterDays,
                onValueChange = {
                    if (it.isBlank()) onRecurAfterDaysChange("")
                    if (it.toDoubleOrNull() == null) return@BasicTextField
                    if (it.toDouble().toInt() > 365) {
                        onRecurAfterDaysChange("365")
                        return@BasicTextField
                    }
                    onRecurAfterDaysChange(it.toDouble().toInt().toString())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimensions.extraSmallPadding()),
                textStyle = TextStyle(
                    fontSize = FontSizes.mediumNonScaledFontSize(),
                    color = Color.DarkGray
                ),
                cursorBrush = SolidColor(Color.DarkGray),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                            .background(AppColors.secondaryWhite)
                            .fillMaxWidth()
                            .padding(Dimensions.mediumPadding())
                    ) {
                        if (recurAfterDays.isBlank()) {
                            Text(
                                text = "Recur after days",
                                fontSize = FontSizes.largeNonScaledFontSize(),
                                color = Color.LightGray,
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}