package com.sparshchadha.expensetracker.feature.bottom_navigation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.home.HomeFragment
import com.sparshchadha.expensetracker.feature.statistics.StatisticsFragment
import com.sparshchadha.expensetracker.feature.transactions.TransactionsFragment
import com.sparshchadha.expensetracker.navigation.ExpenseTrackerNavGraph.BottomBarScreenRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainBottomNavigationBarScreen : Fragment(R.layout.main_bottom_navigation_bar_fragment) {
    private lateinit var navController: NavController
    private lateinit var tvHome: TextView
    private lateinit var tvTransactions: TextView
    private lateinit var tvStatistics: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeNavController()
        initializeViewsUsing(view = view)
        setOnClickListeners()
    }


    private fun initializeNavController() {
        val childNavHostFragment =
            childFragmentManager.findFragmentById(R.id.child_fragment_container) as NavHostFragment
        navController = childNavHostFragment.navController
        navController.graph = createNavGraph(navController)
    }


    private fun createNavGraph(navController: NavController): androidx.navigation.NavGraph {
        return navController.createGraph(
            startDestination = BottomBarScreenRoutes.HOME_SCREEN
        ) {
            fragment<HomeFragment>(BottomBarScreenRoutes.HOME_SCREEN)

            fragment<TransactionsFragment>(BottomBarScreenRoutes.TRANSACTIONS_SCREEN)

            fragment<StatisticsFragment>(BottomBarScreenRoutes.STATISTICS_SCREEN)

        }
    }

    private fun initializeViewsUsing(view: View) {
        tvHome = view.findViewById(R.id.tv_home)
        tvTransactions = view.findViewById(R.id.tv_transactions_history)
        tvStatistics = view.findViewById(R.id.tv_statistics)
    }


    private fun setOnClickListeners() {
        tvHome.setOnClickListener {
            navController.navigate(BottomBarScreenRoutes.HOME_SCREEN) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }

        tvStatistics.setOnClickListener {
            navController.navigate(BottomBarScreenRoutes.STATISTICS_SCREEN) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }

        tvTransactions.setOnClickListener {
            navController.navigate(BottomBarScreenRoutes.TRANSACTIONS_SCREEN) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}