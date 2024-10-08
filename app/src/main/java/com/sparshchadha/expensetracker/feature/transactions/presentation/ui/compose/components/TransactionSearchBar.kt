package com.sparshchadha.expensetracker.feature.transactions.presentation.ui.compose.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions

@Composable
fun TransactionSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    onSearch: () -> Unit,
    onCloseClicked: () -> Unit
) {
    TextField(
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = AppColors.secondaryWhite,
            unfocusedContainerColor = AppColors.secondaryWhite,
            disabledContainerColor = AppColors.secondaryWhite
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.mediumPadding())
            .clip(RoundedCornerShape(Dimensions.cornerRadius()))
            .focusable(enabled = true)
            .focusRequester(focusRequester = focusRequester),
        value = searchQuery,
        onValueChange = {
            onSearchQueryChange(it)
        },
        placeholder = {
            Text(
                color = Color.Gray,
                text = "Search by date, title or description",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        singleLine = true,
        leadingIcon = {
            IconButton(
                onClick = {
                    onSearch()
                    focusManager.clearFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = Color.Gray,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    if (searchQuery.isNotEmpty()) {
                        onSearchQueryChange("")
                    } else {
                        onCloseClicked()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
            }
        )
    )
}

@Preview
@Composable
private fun TransactionSearchBarPrev() {
    var searchQuery by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(key1 = Unit, block = {
        focusRequester.requestFocus()
    })
    TransactionSearchBar(
        searchQuery = searchQuery,
        onSearch = {

        },
        onSearchQueryChange = { newSearchQuery ->
            searchQuery = newSearchQuery
        },
        focusManager = focusManager,
        focusRequester = focusRequester,
        onCloseClicked = {
            focusManager.clearFocus()
        }
    )
}