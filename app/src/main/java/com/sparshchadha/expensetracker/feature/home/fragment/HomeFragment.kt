package com.sparshchadha.expensetracker.feature.home.fragment

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.home.compose.screen.HomeScreen
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {
    private lateinit var homeComposeView: ComposeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        homeComposeView.setContent {
            HomeScreen(
                navigateToNotificationsFragment = {
                    navigateToNotificationsFragment()
                },
                navigateToProfileFragment = {
                    navigateToProfileFragment()
                }
            )
        }
    }


    private fun initializeViewsUsing(view: View) {
        homeComposeView = view.findViewById(R.id.home_compose_view)
    }


    private fun navigateToNotificationsFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(R.id.app_container, NotificationsFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToProfileFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(R.id.app_container, NotificationsFragment())
            .addToBackStack(null)
            .commit()
    }
}