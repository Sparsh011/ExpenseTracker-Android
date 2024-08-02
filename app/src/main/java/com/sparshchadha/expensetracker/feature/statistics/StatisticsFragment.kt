package com.sparshchadha.expensetracker.feature.statistics

import android.os.Bundle
import android.view.View
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.sparshchadha.expensetracker.R

class StatisticsFragment : Fragment(R.layout.statistics_fragment) {
    private lateinit var statisticsComposeView: ComposeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        statisticsComposeView.setContent {
            Text(text = "Statistics screen", color = Color.White)
        }
    }


    private fun initializeViewsUsing(view: View) {
        statisticsComposeView = view.findViewById(R.id.statistics_compose_view)
    }
}