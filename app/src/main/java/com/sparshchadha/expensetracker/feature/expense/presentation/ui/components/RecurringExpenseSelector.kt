package com.sparshchadha.expensetracker.feature.expense.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes

@Composable
fun RecurringExpenseSelector(
    isRecurring: Boolean,
    onRecurringStateChange: (Boolean) -> Unit,
    recurAfterDays: String,
    onRecurAfterDaysChange: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.mediumPadding(), vertical = Dimensions.smallPadding()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Is Recurring", color = Color.Black, fontSize = FontSizes.mediumNonScaledFontSize())

            Spacer(modifier = Modifier.width(Dimensions.smallPadding()))

            Switch(
                checked = isRecurring,
                onCheckedChange = { onRecurringStateChange(!isRecurring) },
                colors = SwitchDefaults.colors(checkedTrackColor = AppColors.primaryColor)
            )
        }

        AnimatedVisibility(visible = isRecurring) {
            OutlinedTextField(
                value = recurAfterDays,
                onValueChange = { onRecurAfterDaysChange(it) },
                label = { Text("Recur After Days") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimensions.smallPadding(), end = Dimensions.largePadding())
            )
        }
    }
}