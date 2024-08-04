package com.sparshchadha.expensetracker.common.ui.components.compose

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

@Composable
fun Pulsating(pulseFraction: Float = 1.1f, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    // if pulseFraction increases, the size of composable during animation will increase. if pulseFraction < initialValue, then the size of composable during animation decreases
    val scaleX by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(modifier = Modifier.scale(scaleX)) {
        content()
    }
}