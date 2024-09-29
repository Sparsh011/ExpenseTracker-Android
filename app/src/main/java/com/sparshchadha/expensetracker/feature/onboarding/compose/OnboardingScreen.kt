package com.sparshchadha.expensetracker.feature.onboarding.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.vibrateDevice
import com.sparshchadha.expensetracker.feature.onboarding.components.compose.OnboardingNavigationSlider
import com.sparshchadha.expensetracker.feature.onboarding.components.compose.OnboardingScreenBackground
import com.sparshchadha.expensetracker.feature.onboarding.components.compose.ShortOnboardingMessage

@Composable
fun OnboardingScreen(
    onNavigateToMainScreen: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val width = displayMetrics.widthPixels.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((0.68 * screenHeight).dp)
        ) {
            OnboardingScreenBackground(screenHeight = screenHeight)
        }

        ShortOnboardingMessage()

        Spacer(modifier = Modifier.height(Dimensions.extraLargePadding()))

        OnboardingNavigationSlider(
            onSlideCompletion = {
                context.vibrateDevice()
                onNavigateToMainScreen()
            },
            screenWidthFloat = width,
            screenWidthDp = screenWidth.dp
        )
    }
}
