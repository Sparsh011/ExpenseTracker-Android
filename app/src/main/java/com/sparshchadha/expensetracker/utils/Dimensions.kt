package com.sparshchadha.expensetracker.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparshchadha.expensetracker.R

object Dimensions {

    @Composable
    fun statusBarPadding(): Dp {
        return dimensionResource(id = R.dimen.status_bar_padding)
    }

    @Composable
    fun thirdIconOnboardingPadding(): Dp {
        return dimensionResource(id = R.dimen.third_icon_onboarding_padding)
    }

    @Composable
    fun smallPadding(): Dp {
        return dimensionResource(id = R.dimen.small_padding)
    }

    @Composable
    fun extraSmallPadding(): Dp {
        return dimensionResource(id = R.dimen.extra_small_padding)
    }

    @Composable
    fun mediumPadding(): Dp {
        return dimensionResource(id = R.dimen.medium_padding)
    }

    @Composable
    fun largePadding(): Dp {
        return dimensionResource(id = R.dimen.large_padding)
    }

    @Composable
    fun extraLargePadding(): Dp {
        return dimensionResource(id = R.dimen.extra_large_padding)
    }

    @Composable
    fun onboardingScreenIconSize(): Dp {
        return dimensionResource(id = R.dimen.onboarding_screen_icon_size)
    }

    @Composable
    fun sliderIconSize(): Dp {
        return dimensionResource(id = R.dimen.slider_icon_size)
    }

    @Composable
    fun sliderContainerSize(): Dp {
        return dimensionResource(id = R.dimen.slider_container_size)
    }

    @Composable
    fun homeScreenTopBarIconSize(): Dp {
        return dimensionResource(id = R.dimen.home_screen_top_bar_icon_size)
    }

    @Composable
    fun largeIconSize(): Dp {
        return dimensionResource(id = R.dimen.large_icon_size)
    }

    @Composable
    fun otpBoxWidth(): Dp {
        return dimensionResource(id = R.dimen.otp_box_width)
    }

    @Composable
    fun topBarHeight(): Dp {
        return dimensionResource(id = R.dimen.top_bar_height)
    }

    @Composable
    fun profilePicSize(): Dp {
        return dimensionResource(id = R.dimen.profile_pic_size)
    }

    @Composable
    fun cornerRadius(): Dp {
        return dimensionResource(id = R.dimen.corner_radius)
    }

    @Composable
    fun errorIconSize(): Dp {
        return dimensionResource(id = R.dimen.error_icon)
    }
}

object FontSizes {

    @Composable
    fun extraSmallFontSize(): Dp {
        return dimensionResource(id = R.dimen.small_text_size)
    }

    @Composable
    fun smallFontSize(): Dp {
        return dimensionResource(id = R.dimen.small_text_size)
    }


    @Composable
    fun mediumFontSize(): Dp {
        return dimensionResource(id = R.dimen.medium_text_size)
    }

    @Composable
    fun largeFontSize(): Dp {
        return dimensionResource(id = R.dimen.large_text_size)
    }

    @Composable
    fun extraLargeFontSize(): Dp {
        return dimensionResource(id = R.dimen.extra_large_text_size)
    }

    val Int.nonScaledSp
        @Composable
        get() = (this / LocalDensity.current.fontScale).sp

    @Composable
    fun smallNonScaledFontSize(): TextUnit {
        return 12.nonScaledSp
    }

    @Composable
    fun mediumNonScaledFontSize(): TextUnit {
        return 16.nonScaledSp
    }


    @Composable
    fun largeNonScaledFontSize(): TextUnit {
        return 20.nonScaledSp
    }


    @Composable
    fun extraLargeNonScaledFontSize(): TextUnit {
        return 24.nonScaledSp
    }
}