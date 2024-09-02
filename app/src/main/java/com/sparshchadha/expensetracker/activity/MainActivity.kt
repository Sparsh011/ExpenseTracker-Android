package com.sparshchadha.expensetracker.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.viewmodel.AuthViewModel
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import com.sparshchadha.expensetracker.navigation.ExpenseTrackerNavGraph
import com.sparshchadha.expensetracker.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    lateinit var parentNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Used to fix the issue of onCreate being called again when configuration changes
            observeUserProfile()
            setWindowAttributes()
        }
    }

    private fun observeUserProfile() {
        profileViewModel.userProfile.asLiveData().observe(this) { profileResponse ->
            profileResponse?.let {
                when (it) {
                    is Resource.Success -> {
                        setGlobalNavGraph(
                            startDestination = ExpenseTrackerNavGraph.MainRoutes.MAIN_BOTTOM_NAVIGATION_SCREEN
                        )
                    }

                    is Resource.Error -> {
                        setGlobalNavGraph(
                            startDestination = ExpenseTrackerNavGraph.MainRoutes.ONBOARDING_SCREEN
                        )
                    }

                    is Resource.Loading -> {
                        // add shimmer or splash
                    }
                }
            }
        }
    }
}