package com.sparshchadha.expensetracker.feature.notifications

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.sparshchadha.expensetracker.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment(R.layout.fragment_notifications) {
    private lateinit var notificationsComposeView: ComposeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view)

        notificationsComposeView.setContent {
            Column {
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
                Text(text = "This is notifications screen")
            }
        }
    }

    private fun initializeViewsUsing(view: View) {
        notificationsComposeView = view.findViewById(R.id.notifications_compose_view)
    }
}