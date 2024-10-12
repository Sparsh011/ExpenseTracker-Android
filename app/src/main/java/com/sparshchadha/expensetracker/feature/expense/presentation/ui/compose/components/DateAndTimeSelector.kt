package com.sparshchadha.expensetracker.feature.expense.presentation.ui.compose.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.common.utils.convert24HourTimeTo12HourTime
import com.sparshchadha.expensetracker.common.utils.convertISODateToUIDate
import com.sparshchadha.expensetracker.common.utils.convertToISOFormat
import com.sparshchadha.expensetracker.common.utils.toMonthString
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@SuppressLint("DefaultLocale")
@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun DateAndTimeSelector(
    hideDatePicker: (Boolean) -> Unit,
    isShowingDateAndTimePicker: Boolean,
    timePickerState: TimePickerState,
    createdOnDate: String,
    createdAtTime: String,
    onDateSelection: (Int, Int, Int) -> Unit,
) {
    var selectedDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    var showCalendar by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimensions.cornerRadius()))
            .background(AppColors.secondaryWhite)
    ) {
        Row(
            modifier = Modifier
                .padding(Dimensions.smallPadding())
                .clickable {
                    hideDatePicker(!isShowingDateAndTimePicker)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    hideDatePicker(!isShowingDateAndTimePicker)
                }, modifier = Modifier.weight(
                    0.2f
                )
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date"
                )
            }

            Text(
                text = createdOnDate.ifBlank {
                    (selectedDate.dayOfMonth.toString() + "-" + selectedDate.monthValue.toMonthString() + "-" + selectedDate.year.toString()
                        .replace(' ', '-')).convertISODateToUIDate()
                }.replace(' ', '-').convertISODateToUIDate(),
                color = Color.Black,
                fontSize = FontSizes.mediumNonScaledFontSize(),
                modifier = Modifier
                    .weight(0.3f)
                    .basicMarquee(5)
            )

            Text(
                text = createdAtTime.convert24HourTimeTo12HourTime(),
                color = Color.Black,
                fontSize = FontSizes.mediumNonScaledFontSize(),
                modifier = Modifier
                    .weight(0.3f)
                    .basicMarquee(5)
            )


            Icon(
                imageVector = if (isShowingDateAndTimePicker) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.weight(0.2f)
            )
        }

        AnimatedVisibility(visible = isShowingDateAndTimePicker) {
            Column {
                TabRow(
                    selectedTabIndex = if (showCalendar) 0 else 1,
                    modifier = Modifier.padding(Dimensions.mediumPadding()),
                    divider = { },
                    containerColor = AppColors.secondaryWhite
                ) {
                    Tab(selected = showCalendar, onClick = {
                        // show calendar only if time picker is being displayed
                        if (!showCalendar) showCalendar = true
                    }) {
                        Text(
                            text = "Select Date",
                            color = Color.Black,
                            fontSize = FontSizes.mediumNonScaledFontSize(),
                            modifier = Modifier.padding(Dimensions.extraSmallPadding())
                        )
                    }

                    Tab(selected = !showCalendar, onClick = {
                        // show time picker only if calendar is being displayed
                        if (showCalendar) showCalendar = false
                    }) {
                        Text(
                            text = "Select Time",
                            color = Color.Black,
                            fontSize = FontSizes.mediumNonScaledFontSize(),
                            modifier = Modifier.padding(Dimensions.extraSmallPadding())
                        )
                    }
                }

                if (showCalendar) {
                    val currentMonth = remember { YearMonth.now() }
                    val startMonth = remember { currentMonth.minusMonths(12) }
                    val endMonth = remember { currentMonth.plusMonths(12) }
                    val firstDayOfWeek =
                        remember { firstDayOfWeekFromLocale() }

                    val state = rememberCalendarState(
                        startMonth = startMonth,
                        endMonth = endMonth,
                        firstVisibleMonth = currentMonth,
                        firstDayOfWeek = firstDayOfWeek
                    )

                    HorizontalCalendar(
                        state = state,
                        dayContent = { content ->
                            Day(content, isSelected = selectedDate == content.date) { day ->
                                selectedDate = day.date
                                onDateSelection(
                                    day.date.year,
                                    day.date.monthValue,
                                    day.date.dayOfMonth
                                )
                            }
                        },
                        monthBody = { _, content ->
                            Box(
                                modifier = Modifier.background(
                                    AppColors.secondaryWhite
                                )
                            ) {
                                content()
                            }
                        },
                        monthHeader = { month ->
                            val daysOfWeek = remember {
                                mutableStateOf(
                                    month.weekDays.first().map { day ->
                                        day.date.dayOfWeek
                                    }
                                )
                            }
                            MonthHeader(daysOfWeek = daysOfWeek.value)
                        },
                        monthContainer = { month, container ->
                            val configuration = LocalConfiguration.current
                            val screenWidth = configuration.screenWidthDp.dp
                            Box(
                                modifier = Modifier
                                    .width(screenWidth)
                                    .padding(8.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Text(
                                        text = month.yearMonth.month.name.lowercase()
                                            .capitalize() + " " + month.yearMonth.year.toString(),
                                        color = Color.DarkGray,
                                        fontSize = FontSizes.largeNonScaledFontSize()
                                    )

                                    Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

                                    container()
                                }
                            }
                        }
                    )
                } else {
                    TimePicker(
                        state = timePickerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }

}


@Composable
fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row {
        for (day in daysOfWeek) {
            Text(
                text = day.name.lowercase().capitalize().substring(0, 3),
                color = Color.Black,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(Dimensions.extraSmallPadding())
            .clip(CircleShape)
            .background(
                if (isSelected) AppColors.primaryColor else AppColors.secondaryWhite
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (day.position == DayPosition.MonthDate) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = if (isSelected) Color.White else Color.Black,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}