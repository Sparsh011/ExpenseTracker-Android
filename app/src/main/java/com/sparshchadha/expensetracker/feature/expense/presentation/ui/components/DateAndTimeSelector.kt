package com.sparshchadha.expensetracker.feature.expense.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.common.utils.convertStrMillisToHumanReadableDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DateAndTimeSelector(
    hideDatePicker: (Boolean) -> Unit,
    isShowingDateAndTimePicker: Boolean,
    createdAt: String,
    combinedDateTimeInMillis: String,
    dateAndTimePagerState: PagerState,
    coroutineScope: CoroutineScope,
    datePickerState: DatePickerState,
    timePickerState: TimePickerState,
) {
    Column(
        modifier = Modifier.background(AppColors.secondaryWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.smallPadding())
                .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                .background(AppColors.secondaryWhite)
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
                text = createdAt.ifBlank { combinedDateTimeInMillis.convertStrMillisToHumanReadableDate() },
                color = Color.Black,
                fontSize = FontSizes.mediumNonScaledFontSize(),
                modifier = Modifier
                    .weight(0.6f)
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
                    selectedTabIndex = dateAndTimePagerState.currentPage,
                    modifier = Modifier.padding(Dimensions.mediumPadding()),
                    divider = { },
                    containerColor = AppColors.secondaryWhite
                ) {
                    Tab(selected = dateAndTimePagerState.currentPage == 0, onClick = {
                        if (dateAndTimePagerState.currentPage != 0) {
                            coroutineScope.launch {
                                dateAndTimePagerState.scrollToPage(0)
                            }
                        }
                    }) {
                        Text(
                            text = "Select Date",
                            color = Color.Black,
                            fontSize = FontSizes.mediumNonScaledFontSize(),
                            modifier = Modifier.padding(Dimensions.extraSmallPadding())
                        )
                    }

                    Tab(selected = dateAndTimePagerState.currentPage == 1, onClick = {
                        if (dateAndTimePagerState.currentPage != 1) {
                            coroutineScope.launch {
                                dateAndTimePagerState.scrollToPage(1)
                            }
                        }
                    }) {
                        Text(
                            text = "Select Time",
                            color = Color.Black,
                            fontSize = FontSizes.mediumNonScaledFontSize(),
                            modifier = Modifier.padding(Dimensions.extraSmallPadding())
                        )
                    }
                }

                HorizontalPager(state = dateAndTimePagerState) {
                    when (it) {
                        0 -> {
                            DatePicker(
                                state = datePickerState,
                                showModeToggle = false
                            )
                        }

                        1 -> {
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
    }
}