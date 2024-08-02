package com.sparshchadha.expensetracker.feature.onboarding

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.sparshchadha.expensetracker.feature.bottom_navigation.MainBottomNavigationBarScreen
import com.sparshchadha.expensetracker.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment: Fragment(R.layout.onboarding_fragment) {
    private lateinit var onboardingComposeView: ComposeView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        onboardingComposeView.setContent {
            Text(text = "Onboarding screen", color = Color.White, fontSize = 20.sp, modifier = Modifier.clickable {
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.parent_fragment_container, MainBottomNavigationBarScreen()
                ).commit()
            })
        }
    }

    private fun initializeViewsUsing(view: View) {
        onboardingComposeView = view.findViewById(R.id.onboarding_compose_view)
    }

}