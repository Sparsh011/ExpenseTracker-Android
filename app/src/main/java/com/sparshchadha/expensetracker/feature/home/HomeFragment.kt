package com.sparshchadha.expensetracker.feature.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment

class HomeFragment: Fragment(R.layout.home_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.home).setOnClickListener {
            navigateToNotificationsFragment()
        }
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