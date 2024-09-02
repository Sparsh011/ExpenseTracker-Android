package com.sparshchadha.expensetracker.feature.auth.ui.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes.nonScaledSp

@Composable
fun ChangeNumberAndCancelView(
    providedPhoneNumber: String,
    onChangeNumber: () -> Unit,
    onCancel: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {


        Spacer(modifier = Modifier.width(Dimensions.smallPadding()))

        NumberView(
            providedPhoneNumber = providedPhoneNumber,
            onChange = onChangeNumber,
            modifier = Modifier.weight(.9f)
        )
    }
}


@Composable
private fun NumberView(
    providedPhoneNumber: String,
    onChange: () -> Unit,
    modifier: Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            buildAnnotatedString {
                append("Not ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(providedPhoneNumber)
                }
                append(" ?")
            },
            color = Color.Black,
            fontSize = 15.nonScaledSp
        )

        Spacer(modifier = Modifier.width(Dimensions.mediumPadding()))

        Text(
            text = "Change",
            modifier = Modifier.clickable { onChange() },
            color = Color.Blue,
            fontSize = 15.nonScaledSp
        )
    }
}