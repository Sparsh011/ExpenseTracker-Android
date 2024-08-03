package com.sparshchadha.expensetracker.feature.auth.ui

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.ui.compose.LoginScreen

class LoginFragment: Fragment(R.layout.login_fragment) {
    private lateinit var loginComposeView: ComposeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsUsing(view = view)

        loginComposeView.setContent {
           LoginScreen()
        }
    }

    private fun initializeViewsUsing(view: View) {
        loginComposeView = view.findViewById(R.id.login_compose_view)
    }
}