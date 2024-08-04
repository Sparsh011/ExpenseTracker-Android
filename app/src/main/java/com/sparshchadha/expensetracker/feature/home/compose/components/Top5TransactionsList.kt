package com.sparshchadha.expensetracker.feature.home.compose.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.utils.AppColors
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes

@Composable
fun Top5TransactionsList() {
    HeaderAndFilterIcon()

    Spacer(
        modifier = Modifier.height(
            Dimensions.mediumPadding()
        )
    )

    for (i in 0 until 5) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(
                    horizontal = Dimensions.mediumPadding(),
                    vertical = Dimensions.extraSmallPadding()
                ),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.primaryWhite
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = Dimensions.largePadding()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = Dimensions.smallPadding())
                        .clip(CircleShape)
                        .size(Dimensions.extraLargePadding())
                        .background(AppColors.secondaryWhite)
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Column(
                    modifier = Modifier.weight(0.65f)
                ) {
                    Text(
                        text = "Top 5 Big Spends",
                        color = Color.DarkGray,
                        modifier = Modifier.padding(
                            start = Dimensions.largePadding(),
                            end = Dimensions.mediumPadding(),
                            bottom = Dimensions.extraSmallPadding()
                        ),
                        fontSize = FontSizes.mediumNonScaledFontSize(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "21 Sept 2024",
                        color = Color.Gray,
                        modifier = Modifier.padding(
                            start = Dimensions.largePadding(),
                            end = Dimensions.mediumPadding()
                        ),
                        fontSize = FontSizes.smallNonScaledFontSize(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = "- ₹45",
                    color = Color.Black,
                    fontSize = FontSizes.mediumNonScaledFontSize(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.15f)
                )

            }
        }
    }
}


@Composable
private fun HeaderAndFilterIcon() {
    var showFiltersDropDown by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Row {
        Text(
            text = "Top 5 Big Spends",
            color = Color.DarkGray,
            modifier = Modifier.padding(
                start = Dimensions.largePadding(),
                end = Dimensions.mediumPadding()
            ),
            fontSize = FontSizes.mediumFontSize().value.sp
        )

        Box {
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.clickable {
                    showFiltersDropDown = true
                }
            )

            FiltersDropDownMenu(
                expanded = showFiltersDropDown,
                onDismiss = {
                    showFiltersDropDown = false
                },
                dropDownWidth = screenWidth / 2,
                onItemSelect = { index ->
                    Toast.makeText(
                        context,
                        "Item $index selected",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}


@Composable
private fun FiltersDropDownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    dropDownWidth: Int,
    onItemSelect: (Int) -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = Modifier
            .background(AppColors.primaryWhite)
            .width(dropDownWidth.dp)
            .padding(start = Dimensions.extraSmallPadding())
    ) {
        for (i in 0..4) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Top 5 Biggest Spends",
                        color = Color.Black,
                        fontSize = FontSizes.mediumNonScaledFontSize()
                    )
                },
                onClick = {
                    onItemSelect(i)
                }
            )
            if (i < 4) HorizontalDivider(color = AppColors.secondaryWhite)
        }
    }
}