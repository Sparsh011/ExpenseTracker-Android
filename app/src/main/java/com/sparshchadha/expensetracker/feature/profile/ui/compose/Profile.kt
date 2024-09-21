package com.sparshchadha.expensetracker.feature.profile.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.FontSizes
import com.sparshchadha.expensetracker.feature.home.ui.compose.screen.Footer
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile

@Composable
fun Profile(
    profile: UserProfile,
    onNameUpdate: (String) -> Unit,
    navigateToExpenseSettingsScreen: () -> Unit,
    onLogout: () -> Unit,
    navigateToNotificationsScreen: () -> Unit,
    userName: String,
    expenseBudget: Int,
) {

    var showNameUpdateDialog by rememberSaveable {
        mutableStateOf(false)
    }

    if (showNameUpdateDialog) {
        NameUpdateDialog(
            currentName = profile.name,
            onNameUpdate = onNameUpdate,
            onDismiss = {
                showNameUpdateDialog = false
            }
        )
    }

    PhoneAndProfilePicture(
        onEdit = {
            showNameUpdateDialog = true
        },
        name = userName,
        profileUri = profile.profileUri,
        phoneNumber = profile.phoneNumber
    )

    Spacer(modifier = Modifier.height(Dimensions.mediumPadding()))

    Text(
        buildAnnotatedString {
            append("Your ")
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Preferences")
            }
        },
        color = Color.Black,
        modifier = Modifier
            .padding(
                horizontal = Dimensions.smallPadding(),
                vertical = Dimensions.mediumPadding()
            ),
        fontSize = FontSizes.largeFontSize().value.sp
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimensions.cornerRadius()))
            .background(AppColors.primaryWhite)
            .padding(Dimensions.extraSmallPadding())
    ) {
        ProfileItem(
            trailingIcon = ImageVector.vectorResource(id = R.drawable.bank_icon),
            trailingText = "Expense budget",
            leadingText = expenseBudget.toString(),
            onItemClick = navigateToExpenseSettingsScreen
        )

        Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

        ProfileItem(
            trailingIcon = Icons.Outlined.Notifications,
            trailingText = "Notifications",
            onItemClick = navigateToNotificationsScreen
        )

        Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

        ProfileItem(
            trailingIcon = ImageVector.vectorResource(id = R.drawable.mail_icon),
            trailingText = profile.emailId.ifBlank { "Link account with email" },
            onItemClick = navigateToExpenseSettingsScreen
        )

        Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

        ProfileItem(
            trailingIcon = ImageVector.vectorResource(id = R.drawable.export_icon),
            trailingText = "Export expenses",
            onItemClick = navigateToExpenseSettingsScreen
        )

        Spacer(modifier = Modifier.padding(bottom = Dimensions.smallPadding()))
    }


    Text(
        buildAnnotatedString {
            append("Extra ")
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Settings")
            }
        },
        color = Color.Black,
        modifier = Modifier
            .padding(
                horizontal = Dimensions.smallPadding(),
                vertical = Dimensions.mediumPadding()
            ),
        fontSize = FontSizes.largeFontSize().value.sp
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimensions.cornerRadius()))
            .background(AppColors.primaryWhite)
            .padding(Dimensions.extraSmallPadding())

    ) {
        ProfileItem(
            trailingIcon = ImageVector.vectorResource(id = R.drawable.playstore_icon),
            trailingText = "Rate us on PlayStore",
            onItemClick = navigateToExpenseSettingsScreen
        )

        Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

        ProfileItem(
            trailingIcon = ImageVector.vectorResource(id = R.drawable.feedback_icon),
            trailingText = "Write feedback",
            onItemClick = navigateToExpenseSettingsScreen
        )

        Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

        ProfileItem(
            trailingIcon = ImageVector.vectorResource(id = R.drawable.privacy_policy_icon),
            trailingText = "Privacy policy",
            onItemClick = navigateToExpenseSettingsScreen
        )

        Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

        ProfileItem(
            trailingIcon = ImageVector.vectorResource(id = R.drawable.group_people_icon),
            trailingText = "Invite friends",
            onItemClick = navigateToExpenseSettingsScreen
        )

        Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

        ProfileItem(
            trailingIcon = ImageVector.vectorResource(id = R.drawable.logout_icon),
            trailingIconTint = AppColors.primaryColor,
            trailingText = "Logout",
            onItemClick = onLogout
        )
    }

    Spacer(modifier = Modifier.height(Dimensions.smallPadding()))

    Footer()
}

@Composable
private fun PhoneAndProfilePicture(
    onEdit: () -> Unit,
    name: String,
    profileUri: String,
    phoneNumber: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = profileUri),
            contentDescription = null,
            modifier = Modifier
                .padding(Dimensions.mediumPadding())
                .size(Dimensions.profilePicSize())
                .clip(CircleShape)
        )

        Column {
            NameAndEditIcon(
                name = name,
                onEdit = onEdit
            )

            Spacer(modifier = Modifier.height(Dimensions.extraSmallPadding()))

            Text(
                text = phoneNumber,
                fontWeight = FontWeight.Normal,
                fontSize = FontSizes.mediumNonScaledFontSize()
            )
        }
    }
}

@Composable
private fun NameAndEditIcon(
    name: String,
    onEdit: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable {
            onEdit()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = FontSizes.extraLargeNonScaledFontSize(),
        )

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.padding(horizontal = Dimensions.mediumPadding())
        )
    }
}