package com.sparshchadha.expensetracker.core.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.ui.screens.xml.FullScreenLoaderFragment
import com.sparshchadha.expensetracker.common.utils.BundleKeys
import com.sparshchadha.expensetracker.feature.auth.presentation.ui.fragments.VerifyOtpFragment
import com.sparshchadha.expensetracker.feature.bottom_navigation.MainBottomNavigationBarFragment
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.fragment.ExpenseFragment
import com.sparshchadha.expensetracker.feature.expense.presentation.ui.fragment.ExpenseSettingsFragment
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import com.sparshchadha.expensetracker.feature.profile.ui.fragments.ProfileFragment

class NavigationProvider(
    private val activity: FragmentActivity,
) {
    fun navigateToMainBottomBarNavigationFragment() {
        activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(
                R.id.parent_fragment_container, MainBottomNavigationBarFragment()
            )
            .commit()
    }

    fun showLoadingFragment() {
        val loaderFragment = FullScreenLoaderFragment()
        loaderFragment.show(activity.supportFragmentManager, "loader")
    }

    fun dismissLoadingFragment() {
        val loaderFragment =
            activity.supportFragmentManager.findFragmentByTag("loader") as? FullScreenLoaderFragment
        loaderFragment?.dismiss()
    }

    fun navigateToVerifyOTPFragment(phoneNumber: String) {
        val fragment = VerifyOtpFragment()
        val bundle = Bundle()
        bundle.putString(BundleKeys.PHONE_NUMBER_KEY, phoneNumber)
        fragment.arguments = bundle

        activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(
                R.id.parent_fragment_container, fragment
            )
            .addToBackStack("VerifyOTPFragment")
            .commit()
    }

    fun navigateToProfileFragment() {
        activity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(R.id.app_container, ProfileFragment())
            .addToBackStack("ProfileFragment")
            .commit()
    }

    fun navigateToNotificationsFragment() {
        activity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(R.id.app_container, NotificationsFragment())
            .addToBackStack("NotificationsFragment")
            .commit()
    }

    fun navigateToExpenseFragment(id: Int) {
        val fragment = ExpenseFragment()
        if (id != -1) {
            val bundle = Bundle()
            bundle.putInt(BundleKeys.EXPENSE_DB_ID_KEY, id)
            fragment.arguments = bundle
        }

        activity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_down
            )
            .add(R.id.app_container, fragment)
            .addToBackStack("ExpenseFragment")
            .commit()
    }

    fun navigateToExpenseSettingsFragment(currentExpenseBudget: Double) {
        val fragment = ExpenseSettingsFragment()
        fragment.arguments = Bundle().apply {
            putDouble(BundleKeys.EXPENSE_BUDGET_KEY, currentExpenseBudget)
        }

        activity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .add(R.id.app_container, fragment)
            .addToBackStack("ExpenseSettingsFragment")
            .commit()
    }
}