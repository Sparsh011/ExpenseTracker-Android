package com.sparshchadha.expensetracker.feature.home.compose.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes

@Composable
fun CurrentDayExpenses() {
    Text(
        text = "Today's Transactions",
        color = Color.DarkGray,
        modifier = Modifier.padding(
            start = Dimensions.largePadding(),
            end = Dimensions.mediumPadding()
        ),
        fontSize = FontSizes.mediumFontSize().value.sp
    )

    NoTransactionAnimation()
//    for (i in 0..3) {
//        ExpenseCard()
//    }
}

@Composable
fun NoTransactionAnimation() {
    // TODO - Add lottie composition state in viewmodel so that it does not recompose on every bottom fragment navigation
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_box_lottie_anim))

    var noTransactionsFontSize by remember {
        mutableFloatStateOf(0f)
    }

    LaunchedEffect(key1 = Unit) {
        noTransactionsFontSize = 24f
    }

    Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "No Transaction Today!",
            color = Color.Black,
            fontSize = noTransactionsFontSize.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = StiffnessMediumLow)
                )
        )
        val animProgress by animateLottieCompositionAsState(
            composition = lottieComposition,
            iterations = 1
        )
        LottieAnimation(
            composition = lottieComposition,
            progress = { animProgress },
            modifier = Modifier.size(250.dp)
        )
    }
}
