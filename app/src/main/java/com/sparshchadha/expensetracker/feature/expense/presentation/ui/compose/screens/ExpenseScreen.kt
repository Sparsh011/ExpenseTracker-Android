package com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.screens

import android.annotation.SuppressLint
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.common.ui.components.compose.ETTopBar
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Constants
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.common.utils.convertToISOFormat
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components.DateAndTimeSelector
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components.ExpenseCategorySelector
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components.ExpenseFieldInputFields
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components.RecurringExpenseSelector
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(
    expenseEntity: ExpenseEntity? = null,
    onSaveExpense: (ExpenseEntity) -> Unit,
    onCancel: () -> Unit,
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var isRecurring by rememberSaveable { mutableStateOf(false) }
    var recurAfterDays by rememberSaveable { mutableStateOf("") }
    val currency by remember { mutableStateOf("INR") }

    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
        is24Hour = false,
    )

    var createdOnDate by remember {
        mutableStateOf(expenseEntity?.createdOnDate?.takeIf { it.isNotBlank() } ?: "")
    }

    var createdAtTime by remember {
        mutableStateOf(expenseEntity?.createdAtTime?.takeIf { it.isNotBlank() } ?: "")
    }

    val updatedOnDate by remember {
        mutableStateOf("")
    }

    val updatedAtTime by remember {
        mutableStateOf("")
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    LaunchedEffect(key1 = timePickerState.hour, key2 = timePickerState.minute) {
        createdAtTime = String.format(
            "%02d:%02d",
            timePickerState.hour,
            timePickerState.minute
        )
    }

    LaunchedEffect(expenseEntity) {
        expenseEntity?.let {
            title = it.title
            description = it.description
            amount = it.amount.toString()
            category = it.category
            isRecurring = it.isRecurring
            recurAfterDays = it.recurAfterDays.toString()
            createdOnDate = it.createdOnDate
            createdAtTime = it.createdAtTime
        }
    }

    Column(
        modifier = Modifier
            .background(AppColors.secondaryWhite)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ETTopBar(
            text = if (expenseEntity != null) "Update Expense" else "Create Expense",
            onBackPress = onCancel,
            isBackEnabled = true,
            modifier = Modifier
                .statusBarsPadding()
                .height(Dimensions.topBarHeight())
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .background(AppColors.primaryWhite)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ExpenseFieldInputFields(
                title = title,
                description = description,
                amount = amount,
                descriptionTextFieldHeight = (screenHeight / 6),
                onTitleChange = {
                    if (it.length < 25) title = it
                },
                onDescriptionChange = {
                    description = it
                },
                onAmountChange = {
                    amount = it
                }
            )

            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

            RecurringExpenseSelector(
                isRecurring = isRecurring,
                onRecurringStateChange = {
                    isRecurring = it
                },
                recurAfterDays = recurAfterDays,
                onRecurAfterDaysChange = {
                    recurAfterDays = it
                }
            )

            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

            ExpenseCategorySelector(category) {
                category = it
            }

            Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

            DateAndTimeSelector(
                hideDatePicker = { showDatePicker = it },
                isShowingDateAndTimePicker = showDatePicker,
                timePickerState = timePickerState,
                createdOnDate = createdOnDate,
                createdAtTime = createdAtTime,
                onDateSelection = { year, month, day ->
                    createdOnDate = "$day $month $year"
                },
            )

            Button(
                onClick = {
                    val expense = ExpenseEntity(
                        expenseId = expenseEntity?.expenseId,
                        createdOnDate = createdOnDate.convertToISOFormat(),
                        createdAtTime = String.format(
                            "%02d:%02d",
                            timePickerState.hour,
                            timePickerState.minute
                        ).lowercase(),
                        updatedOnDate = updatedOnDate,
                        updatedAtTime = updatedAtTime,
                        amount = amount.trim().toDoubleOrNull() ?: 0.0,
                        category = category.trim(),
                        isRecurring = isRecurring,
                        recurAfterDays = recurAfterDays.toIntOrNull() ?: -1,
                        currency = currency,
                        title = title.trim(),
                        description = description.trim()
                    )
                    onSaveExpense(expense)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.primaryColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.mediumPadding())
            ) {
                Text(
                    "Save",
                    color = Color.White,
                    fontSize = FontSizes.mediumFontSize().value.sp
                )
            }
        }
    }
}