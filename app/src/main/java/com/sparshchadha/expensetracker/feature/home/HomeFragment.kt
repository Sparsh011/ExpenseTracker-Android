package com.sparshchadha.expensetracker.feature.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment

class HomeFragment: Fragment(R.layout.home_fragment) {
    private lateinit var homeComposeView: ComposeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        homeComposeView.setContent {
            Text(text = "Home Screen", color = Color.White, modifier = Modifier.clickable {
                navigateToNotificationsFragment()
            })
        }
    }


    private fun initializeViewsUsing(view: View) {
        homeComposeView = view.findViewById(R.id.home_compose_view)
    }


    private fun navigateToNotificationsFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            ?.replace(R.id.app_container, NotificationsFragment())
            ?.addToBackStack(null)
            ?.commit()
    }
}