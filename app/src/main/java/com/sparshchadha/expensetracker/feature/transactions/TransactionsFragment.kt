package com.sparshchadha.expensetracker.feature.transactions

import android.os.Bundle
import android.view.View
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.sparshchadha.expensetracker.R

class TransactionsFragment : Fragment(R.layout.transactions_fragment) {
    private lateinit var transactionsComposeView: ComposeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewsUsing(view = view)

        transactionsComposeView.setContent {
            Text(text = "Transactions screen", fontSize = 20.sp, color = Color.White)
        }
    }


    private fun initializeViewsUsing(view: View) {
        transactionsComposeView = view.findViewById(R.id.transactions_compose_view)
    }
}