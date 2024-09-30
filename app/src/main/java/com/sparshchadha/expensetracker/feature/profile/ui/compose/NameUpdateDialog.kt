package com.sparshchadha.expensetracker.feature.profile.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.common.utils.Utility.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameUpdateDialog(currentName: String, onNameUpdate: (String) -> Unit, onDismiss: () -> Unit) {
    var newName by rememberSaveable {
        mutableStateOf(currentName)
    }

    BasicAlertDialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                .background(Color.White)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    buildAnnotatedString {
                        append("Current ")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(currentName)
                        }
                    },
                    color = Color.Black,
                    modifier = Modifier
                        .padding(
                            horizontal = Dimensions.largePadding(),
                            vertical = Dimensions.mediumPadding()
                        ),
                    fontSize = FontSizes.mediumNonScaledFontSize()
                )

                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier
                        .noRippleClickable {
                            onDismiss()
                        }
                        .padding(
                            horizontal = Dimensions.largePadding(),
                            vertical = Dimensions.mediumPadding()
                        )
                )
            }


            Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                modifier = Modifier
                    .padding(horizontal = Dimensions.largePadding())
                    .fillMaxWidth(),
                label = {
                    Text(text = "Your name")
                }
            )

            Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

            Button(
                onClick = {
                    onNameUpdate(newName)
                },
                modifier = Modifier
                    .padding(horizontal = Dimensions.largePadding())
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.primaryColor
                )
            ) {
                Text(text = "Update", color = Color.White)
            }

            Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

        }
    }
}