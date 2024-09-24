package com.sparshchadha.expensetracker.feature.expense.presentation.ui.screens

import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.common.ui.components.compose.ETTopBar
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.common.utils.convertMillisToDate
import com.sparshchadha.expensetracker.common.utils.convertStrMillisToHumanReadableDate
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.components.DateAndTimeSelector
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.components.ExpenseCategorySelector
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.components.ExpenseFieldInputFields
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.components.RecurringExpenseSelector


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExpenseScreen(
    expenseEntity: ExpenseEntity? = null,
    onSaveExpense: (ExpenseEntity) -> Unit,
    onCancel: () -> Unit,
) {
    var title by remember { mutableStateOf(expenseEntity?.title ?: "") }
    var description by remember { mutableStateOf(expenseEntity?.description ?: "") }
    var amount by remember { mutableStateOf(expenseEntity?.amount?.toString() ?: "") }
    var category by remember { mutableStateOf(expenseEntity?.category ?: "") }
    var isRecurring by remember { mutableStateOf(expenseEntity?.isRecurring ?: false) }
    var recurAfterDays by remember {
        mutableStateOf(
            expenseEntity?.recurAfterDays?.toString() ?: ""
        )
    }
    val currency by remember { mutableStateOf(expenseEntity?.currency ?: "INR") }

    val createdAt by remember {
        mutableStateOf("")
    }
    val updatedAt by remember { mutableStateOf(expenseEntity?.updatedAt ?: "") }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Calendar.getInstance().apply {
            timeZone = TimeZone.getDefault()
        }.timeInMillis
    )

    val timePickerState = rememberTimePickerState(
        initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
        is24Hour = false,
    )
    val dateAndTimePagerState = rememberPagerState {
        2
    }
    val coroutineScope = rememberCoroutineScope()

    val combinedDateTimeInMillis = combineDateAndTime(
        datePickerState.selectedDateMillis,
        timePickerState
    )

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp


    Column(
        modifier = Modifier
            .background(AppColors.secondaryWhite)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ETTopBar(
            text = if (expenseEntity != null) "Update Expense" else "create Expense",
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
                createdAt = createdAt,
                combinedDateTimeInMillis = (combinedDateTimeInMillis ?: 0L).toString(),
                dateAndTimePagerState = dateAndTimePagerState,
                coroutineScope = coroutineScope,
                datePickerState = datePickerState,
                timePickerState = timePickerState
            )

            if (updatedAt.isNotBlank()) {
                Text(
                    text = "Last Updated: ${updatedAt.convertStrMillisToHumanReadableDate()}",
                    modifier = Modifier.padding(Dimensions.smallPadding()),
                    fontSize = FontSizes.mediumFontSize().value.sp,
                    color = Color.Black
                )
            }

            Button(
                onClick = {
                    val expense = ExpenseEntity(
                        createdAt = combinedDateTimeInMillis?.convertMillisToDate() ?: "",
                        updatedAt = if (updatedAt.isBlank()) "" else combinedDateTimeInMillis?.convertMillisToDate() ?: "",
                        amount = amount.trim().toDoubleOrNull() ?: 0.0,
                        category = category.trim(),
                        isRecurring = isRecurring,
                        recurAfterDays = recurAfterDays.toIntOrNull() ?: -1,
                        currency = currency,
                        title = title.trim(),
                        description = description.trim()
                    )
                    if (expense.isValid()) {
                        onSaveExpense(expense)
                    }
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

@OptIn(ExperimentalMaterial3Api::class)
fun combineDateAndTime(dateMillis: Long?, timePickerState: TimePickerState): Long? {
    dateMillis?.let {
        val calendar = Calendar.getInstance().apply {
            this.timeInMillis = dateMillis // Set the selected date in milliseconds
            this.set(Calendar.HOUR_OF_DAY, timePickerState.hour) // Set the selected hour
            this.set(Calendar.MINUTE, timePickerState.minute) // Set the selected minute
            this.set(Calendar.SECOND, 0) // Optional: Set seconds to 0
            this.set(Calendar.MILLISECOND, 0) // Optional: Set milliseconds to 0
        }
        return calendar.timeInMillis
    }
    return null
}


@Preview
@Composable
private fun ExpensePrev() {
    ExpenseScreen(onSaveExpense = {}) {

    }
}