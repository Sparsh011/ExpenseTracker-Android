package com.sparshchadha.expensetracker.feature.profile.ui

import android.os.Bundle
import android.view.View
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.sparshchadha.expensetracker.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.profile_fragment) {
    private lateinit var profileComposeView: ComposeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        profileComposeView.setContent {
            Text(text = "Profile screen", color = Color.White)
        }
    }


    private fun initializeViewsUsing(view: View) {
        profileComposeView = view.findViewById(R.id.profile_compose_view)
    }
}