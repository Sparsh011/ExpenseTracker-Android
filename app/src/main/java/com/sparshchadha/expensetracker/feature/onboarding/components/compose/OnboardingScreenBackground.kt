package com.sparshchadha.expensetracker.feature.onboarding.components.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.ui.components.compose.Pulsating
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions

@Composable
fun OnboardingScreenBackground(
    screenHeight: Int,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height((0.6 * screenHeight).dp)

    ) {
        Background(height = (0.6 * screenHeight).toInt())

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Pulsating {
                Image(
                    painter = painterResource(id = R.drawable.dollar_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(
                            start = Dimensions.largePadding(),
                            top = Dimensions.statusBarPadding() + Dimensions.largePadding()
                        )
                        .size(Dimensions.onboardingScreenIconSize())
                )
            }

            Box {
                Image(
                    painter = painterResource(id = R.drawable.onboarding_bg_image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(
                            top = Dimensions.statusBarPadding() + Dimensions.largePadding()
                        )
                        .fillMaxHeight(),
                    contentScale = ContentScale.FillHeight
                )

                Pulsating {
                    Image(
                        painter = painterResource(id = R.drawable.white_graph),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(
                                start = Dimensions.thirdIconOnboardingPadding(),
                                top = Dimensions.statusBarPadding() + Dimensions.largePadding() + Dimensions.smallPadding()
                            )
                            .size(Dimensions.onboardingScreenIconSize())
                    )
                }
            }
        }
    }
}

@Composable
private fun Background(height: Int) {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val width = displayMetrics.widthPixels.toFloat()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
    ) {
        val figureHeight = size.height

        val leftTop = Offset(0f, 0f)
        val rightTop = Offset(width, 0f)
        val leftBottom = Offset(0f, figureHeight)
        val rightBottom = Offset(width, figureHeight + (figureHeight / 8))

        // Create a path for the figure, which is basically a trapezium
        val path = Path().apply {
            moveTo(leftTop.x, leftTop.y)
            lineTo(rightTop.x, rightTop.y)
            lineTo(rightBottom.x, rightBottom.y)
            lineTo(leftBottom.x, leftBottom.y)
            close()
        }

        drawPath(
            path = path,
            color = AppColors.primaryColor,
            style = Fill
        )
    }
}