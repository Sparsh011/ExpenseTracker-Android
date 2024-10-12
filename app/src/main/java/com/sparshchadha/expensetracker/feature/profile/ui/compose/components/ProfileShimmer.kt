package com.sparshchadha.expensetracker.feature.profile.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sparshchadha.expensetracker.common.ui.components.compose.Shimmer
import com.sparshchadha.expensetracker.common.utils.Dimensions

@Composable
fun ProfileShimmer() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(Dimensions.profilePicSize())
                    .background(brush = Shimmer(showShimmer = true, targetValue = 500f))
            )

            Spacer(modifier = Modifier.width(Dimensions.mediumPadding()))

            Column {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                        .background(brush = Shimmer(showShimmer = true, targetValue = 500f))
                )

                Spacer(modifier = Modifier.height(Dimensions.extraSmallPadding()))

                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(200.dp)
                        .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                        .background(brush = Shimmer(showShimmer = true, targetValue = 500f))
                )
            }

            Spacer(modifier = Modifier.width(Dimensions.mediumPadding()))
        }

        Spacer(modifier = Modifier.height(Dimensions.largePadding()))

        Box(
            modifier = Modifier
                .padding(
                    horizontal = Dimensions.largePadding(),
                    vertical = Dimensions.smallPadding()
                )
                .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                .fillMaxWidth()
                .height(50.dp)
                .background(brush = Shimmer(showShimmer = true, targetValue = 500f)),
        )

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Box(
            modifier = Modifier
                .padding(
                    horizontal = Dimensions.largePadding(),
                    vertical = Dimensions.smallPadding()
                )
                .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                .fillMaxWidth()
                .height(200.dp)
                .background(brush = Shimmer(showShimmer = true, targetValue = 500f)),
        )

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

        Box(
            modifier = Modifier
                .padding(
                    horizontal = Dimensions.largePadding(),
                    vertical = Dimensions.smallPadding()
                )
                .clip(RoundedCornerShape(Dimensions.cornerRadius()))
                .fillMaxWidth()
                .height(200.dp)
                .background(brush = Shimmer(showShimmer = true, targetValue = 500f)),
        )

        Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))
    }
}
