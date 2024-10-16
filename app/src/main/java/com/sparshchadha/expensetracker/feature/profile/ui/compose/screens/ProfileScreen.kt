package com.sparshchadha.expensetracker.feature.profile.ui.compose.screens

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
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.ui.compose.components.Profile
import com.sparshchadha.expensetracker.feature.profile.ui.compose.components.ProfileFetchError
import com.sparshchadha.expensetracker.feature.profile.ui.compose.components.ProfileShimmer

@Composable
fun ProfileScreen(
    profileState: UserProfile?,
    userName: String,
    expenseBudget: Double,
    onBackPress: () -> Unit,
    onRetryProfileFetch: () -> Unit,
    showLoader: Boolean,
    showError: Boolean,
    onNameUpdate: (String) -> Unit,
    navigateToExpenseSettingsScreen: () -> Unit,
    onLogout: () -> Unit,
    navigateToNotificationsScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.secondaryWhite)
    ) {
        ETTopBar(
            text = "Profile",
            isBackEnabled = true,
            onBackPress = onBackPress,
            modifier = Modifier
                .background(AppColors.primaryWhite)
                .statusBarsPadding()
                .height(Dimensions.topBarHeight())
                .fillMaxWidth()
        )

        if (showLoader) {
            ProfileShimmer()
        }

        if (showError) {
            ProfileFetchError(onRetryClick = onRetryProfileFetch)
        }

        profileState?.let {
            Column(
                modifier = Modifier
                    .background(AppColors.secondaryWhite)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),

                ) {
                Profile(
                    profile = it,
                    userName = userName,
                    expenseBudget = expenseBudget,
                    onNameUpdate = onNameUpdate,
                    navigateToExpenseSettingsScreen = navigateToExpenseSettingsScreen,
                    onLogout = onLogout,
                    navigateToNotificationsScreen = navigateToNotificationsScreen
                )
            }
        }
    }
}

