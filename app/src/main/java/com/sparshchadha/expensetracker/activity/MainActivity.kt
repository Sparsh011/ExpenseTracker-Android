package com.sparshchadha.expensetracker.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.otpless.main.OtplessManager
import com.otpless.main.OtplessView
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.Constants
import com.sparshchadha.expensetracker.core.navigation.ExpenseTrackerNavGraph
import com.sparshchadha.expensetracker.feature.auth.presentation.ui.fragments.LoginFragment
import com.sparshchadha.expensetracker.feature.auth.presentation.viewmodel.AuthViewModel
import com.sparshchadha.expensetracker.feature.bottom_navigation.MainBottomNavigationBarFragment
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import com.sparshchadha.expensetracker.feature.onboarding.presentation.fragments.OnboardingFragment
import com.sparshchadha.expensetracker.feature.profile.ui.fragments.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var parentNavController: NavController
    private lateinit var otplessView: OtplessView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initializeOtpless()

        if (savedInstanceState == null) {
            // Used to fix the issue of onCreate being called again when configuration changes
            setupNavigation()
            setWindowAttributes()
        }
    }

    private fun setupNavigation() {
        val accessToken = authViewModel.getAccessToken()

        if (accessToken.isBlank()) {
            setGlobalNavGraph(
                startDestination = ExpenseTrackerNavGraph.MainRoutes.ONBOARDING_SCREEN
            )
        } else {
            setGlobalNavGraph(
                startDestination = ExpenseTrackerNavGraph.MainRoutes.MAIN_BOTTOM_NAVIGATION_SCREEN
            )
        }
    }

    private fun setGlobalNavGraph(
        startDestination: String,
    ) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.parent_fragment_container) as NavHostFragment
        parentNavController = navHostFragment.navController

        parentNavController.graph = parentNavController.createGraph(
            startDestination = startDestination
        ) {
            fragment<OnboardingFragment>(ExpenseTrackerNavGraph.MainRoutes.ONBOARDING_SCREEN)

            fragment<MainBottomNavigationBarFragment>(ExpenseTrackerNavGraph.MainRoutes.MAIN_BOTTOM_NAVIGATION_SCREEN)

            fragment<NotificationsFragment>(ExpenseTrackerNavGraph.MainRoutes.NOTIFICATIONS_SCREEN)

            fragment<ProfileFragment>(ExpenseTrackerNavGraph.MainRoutes.PROFILE_SCREEN)

            fragment<LoginFragment>(ExpenseTrackerNavGraph.MainRoutes.LOGIN_SCREEN)
        }
    }

    private fun setWindowAttributes() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.app_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = systemBars.bottom)
            insets
        }

        WindowCompat.getInsetsController(
            window,
            findViewById(R.id.app_container)
        ).isAppearanceLightStatusBars = true
    }

    private fun initializeOtpless() {
        otplessView = OtplessManager.getInstance().getOtplessView(this)
        otplessView.initHeadless(Constants.OTPLESS_APPID)
    }
}