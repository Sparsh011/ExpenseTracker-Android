package com.sparshchadha.expensetracker.feature.profile.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sparshchadha.expensetracker.common.ui.components.compose.ETTopBar
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.Resource

@Composable
fun ProfileScreen(
    profileState: Resource<UserProfile>?,
    onBackPress: () -> Unit,
    onRetryProfileFetch: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        ETTopBar(
            text = "Profile",
            isBackEnabled = true,
            onBackPress = onBackPress,
            modifier = Modifier
                .statusBarsPadding()
                .background(AppColors.secondaryWhite)
                .height(Dimensions.topBarHeight())
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.primaryWhite)
                .verticalScroll(rememberScrollState())

        ) {
            when (profileState) {
                is Resource.Success -> {
                    profileState.data?.let {
                        Profile(it)
                    } ?: run {
                        ProfileError(
                            error = "Couldn't Fetch Your Profile",
                            errorMessage = profileState.error?.message
                                ?: "Something Went Wrong, Please Try Again!",
                            canRetry = true,
                            onRetryProfileFetch = onRetryProfileFetch
                        )
                    }
                }

                is Resource.Error -> {
                    ProfileError(
                        error = "Couldn't Fetch Your Profile",
                        errorMessage = profileState.error?.message
                            ?: "Something Went Wrong, Please Try Again!",
                        canRetry = true,
                        onRetryProfileFetch = onRetryProfileFetch
                    )
                }

                is Resource.Loading -> {
                    ProfileShimmer()
                }

                null -> {
                    // Do nothing, UserProfile was initially null just to give an initial value which may or
                    // may not be utilized later.
                }
            }
        }
    }
}