package com.sparshchadha.expensetracker.feature.profile.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sparshchadha.expensetracker.common.ui.components.compose.ETTopBar
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.showToast

@Composable
fun ProfileScreen(
    profileState: UserProfile?,
    onBackPress: () -> Unit,
    onRetryProfileFetch: () -> Unit,
    showLoader: Boolean,
    showError: Boolean,
    onNameUpdate: (String) -> Unit
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

        if (showLoader) {
            CircularProgressIndicator()
        }

        if (showError) {
            LocalContext.current.showToast("Error aa gya")
        }

        profileState?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.primaryWhite)
                    .verticalScroll(rememberScrollState()),

                ) {
                Profile(profile = it, onNameUpdate = onNameUpdate)
            }
        }
    }
}