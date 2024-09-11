package com.sparshchadha.expensetracker.feature.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.ui.fragments.LoginFragment
import com.sparshchadha.expensetracker.feature.onboarding.compose.OnboardingScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class OnboardingFragment : Fragment(R.layout.onboarding_fragment) {
    private lateinit var onboardingComposeView: ComposeView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        onboardingComposeView.setContent {
            OnboardingScreen(
                onNavigateToMainScreen = {
                    navigateToLoginScreen()
                }
            )
        }
    }

    private fun initializeViewsUsing(view: View) {
        onboardingComposeView = view.findViewById(R.id.onboarding_compose_view)
    }

    private fun navigateToLoginScreen() {
        lifecycleScope.launch {
            delay(500L)
            withContext(Dispatchers.Main) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in, R.anim.fade_out,
                        R.anim.fade_in, R.anim.slide_out
                    )
                    .replace(
                        R.id.parent_fragment_container, LoginFragment()
                    )
                    .commit()
            }
        }
    }
}