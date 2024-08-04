package com.sparshchadha.expensetracker.feature.onboarding.components.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.sparshchadha.expensetracker.utils.AppColors
import com.sparshchadha.expensetracker.utils.Dimensions
import com.sparshchadha.expensetracker.utils.FontSizes
import kotlin.math.min


@Composable
fun OnboardingNavigationSlider(
    onSlideCompletion: () -> Unit,
    screenWidthFloat: Float,
    screenWidthDp: Dp,
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        GetStartedSlider(
            screenWidthDp = screenWidthDp,
            screenWidthFloat = screenWidthFloat,
            onSlideComplete = onSlideCompletion
        )
    }
}


@Composable
private fun GetStartedSlider(
    screenWidthDp: Dp,
    screenWidthFloat: Float,
    onSlideComplete: () -> Unit,
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val maxOffsetX = (0.5f * screenWidthFloat)
    var isSlideComplete by rememberSaveable {
        mutableStateOf(false)
    }

    val sliderColor = animateColorAsState(
        targetValue = getSliderContainerColor(currentOffset = offsetX, maxOffset = maxOffsetX),
        animationSpec = tween(durationMillis = 500, easing = EaseInOut),
        label = ""
    )

    val iconRotationDegrees by animateFloatAsState(
        targetValue = if (offsetX >= maxOffsetX * 0.8f) 360f else min(
            offsetX / maxOffsetX * 270f,
            360f
        ),
        animationSpec = tween(durationMillis = 50, easing = LinearEasing),
        label = ""
    )

    val sliderIcon =
        if (offsetX >= maxOffsetX * 0.8f) Icons.Default.Check else Icons.AutoMirrored.Default.KeyboardArrowRight

    Box(
        modifier = Modifier
            .width((0.65 * screenWidthDp))
            .height(Dimensions.sliderContainerSize())
            .background(
                sliderColor.value,
                RoundedCornerShape(Dimensions.largePadding())
            )
            .padding(5.dp),
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), 0) }
                .background(AppColors.primaryColor, CircleShape)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (offsetX == maxOffsetX) return@detectHorizontalDragGestures
                            offsetX = 0f
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            offsetX = (offsetX + dragAmount).coerceIn(0f, maxOffsetX)
                            if (offsetX >= maxOffsetX && !isSlideComplete) {
                                isSlideComplete = true
                                onSlideComplete()
                            }
                        })
                }
        ) {
            Icon(
                imageVector = sliderIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(Dimensions.sliderIconSize())
                    .graphicsLayer {
                        rotationZ = iconRotationDegrees
                    }
                    .alpha(
                        if (iconRotationDegrees == 0f || iconRotationDegrees == 360f) 1f
                        else if (offsetX == 0.8f * maxOffsetX || offsetX == 0.79f * maxOffsetX || offsetX == 0.81f * maxOffsetX) 0f
                        else 0.5f
                    )
            )
        }

        Text(
            text = "Let's Go",
            fontSize = FontSizes.extraSmallFontSize().value.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.Bold
        )
    }
}


private fun getSliderContainerColor(currentOffset: Float, maxOffset: Float): Color {
    if (currentOffset == 0f) {
        return Color.Gray
    }

    if (currentOffset >= 0.6f * maxOffset) return AppColors.primaryGreen

    return Color.Gray
}