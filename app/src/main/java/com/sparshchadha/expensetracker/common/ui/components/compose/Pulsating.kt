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
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Use this to add a pulsating effect to a composable.
 * We are using [Modifier.graphicsLayer] instead of [Modifier.scale] because the latter triggers recomposition every time the composable shrinks or expands,
 * which leads to more than 60 recompositions per second.
 * @param pulseFraction A floating number indicating by how much the composable should scale (either shrink or expand)
 * @param content A composable lambda that will be used to display the content you wish to have a pulsating effect.
 */
@Composable
fun Pulsating(pulseFraction: Float = 1.1f, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    // if pulseFraction increases, the size of composable during animation will increase. if pulseFraction < initialValue, then the size of composable during animation decreases
    val animationScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(modifier = Modifier.graphicsLayer {
        scaleX = animationScale
        scaleY = animationScale
    }) {
        content()
    }
}