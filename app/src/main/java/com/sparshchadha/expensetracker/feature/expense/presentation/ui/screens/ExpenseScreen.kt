package com.sparshchadha.expensetracker.feature.expense.presentation.ui.screens

import android.annotation.SuppressLint
import android.icu.util.Calendar
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.common.ui.components.compose.ETTopBar
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Constants
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.common.utils.convertStrMillisToHumanReadableDate
import com.sparshchadha.expensetracker.common.utils.convertToHumanReadableDate
import com.sparshchadha.expensetracker.feature.expense.domain.entity.ExpenseEntity
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.components.DateAndTimeSelector
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.components.ExpenseCategorySelector
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.components.ExpenseFieldInputFields
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.components.RecurringExpenseSelector
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExpenseScreen(
    expenseEntity: ExpenseEntity? = null,
    onSaveExpense: (ExpenseEntity) -> Unit,
    onCancel: () -> Unit,
    isNewExpense: Boolean,
) {
    var title by rememberSaveable { mutableStateOf(expenseEntity?.title ?: "") }
    var description by rememberSaveable { mutableStateOf(expenseEntity?.description ?: "") }
    var amount by rememberSaveable { mutableStateOf(expenseEntity?.amount?.toString() ?: "") }
    var category by rememberSaveable { mutableStateOf(expenseEntity?.category ?: "") }
    var isRecurring by rememberSaveable { mutableStateOf(expenseEntity?.isRecurring ?: false) }
    var recurAfterDays by rememberSaveable {
        mutableStateOf(
            expenseEntity?.recurAfterDays?.toString() ?: ""
        )
    }
    val currency by rememberSaveable { mutableStateOf(expenseEntity?.currency ?: "INR") }

    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = parseDateToMillis()
    )

    val timePickerState = rememberTimePickerState(
        initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        initialMinute = Calendar.getInstance().get(Calendar.MINUTE),
        is24Hour = false,
    )

    val createdOnDate by rememberSaveable {
        mutableStateOf(expenseEntity?.createdOnDate?.takeIf { it.isNotBlank() } ?: "")
    }

    val createdAtTime by rememberSaveable {
        mutableStateOf(expenseEntity?.createdAtTime?.takeIf { it.isNotBlank() } ?: "")
    }

    val updatedOnDate by rememberSaveable {
        mutableStateOf("")
    }

    val updatedAtTime by rememberSaveable {
        mutableStateOf("")
    }

    val dateAndTimePagerState = rememberPagerState {
        2
    }
    val coroutineScope = rememberCoroutineScope()

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
                dateAndTimePagerState = dateAndTimePagerState,
                coroutineScope = coroutineScope,
                datePickerState = datePickerState,
                timePickerState = timePickerState,
                createdOnDate = createdOnDate,
                createdAtTime = createdAtTime,
                updatedOnDate = updatedOnDate,
                updatedAtTime = updatedAtTime,
                isNewExpense = isNewExpense
            )

            if (!isNewExpense) {
                Text(
                    text = "Last Updated: $updatedOnDate $updatedAtTime",
                    modifier = Modifier.padding(Dimensions.smallPadding()),
                    fontSize = FontSizes.mediumFontSize().value.sp,
                    color = Color.Black
                )
            }

            Button(
                onClick = {
                    val expense = ExpenseEntity(
                        createdOnDate = datePickerState.selectedDateMillis?.convertToHumanReadableDate()?.lowercase()
                            ?: "",
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

fun parseDateToMillis(): Long {
    val formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER_PATTERN)

    val localDate = LocalDate.parse(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER_PATTERN)),
        formatter
    )

    val zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault())

    return Date.from(zonedDateTime.toInstant()).time
}