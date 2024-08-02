package com.sparshchadha.expensetracker.activity

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.navigation.NavGraph

internal fun MainActivity.setNavGraph() {
    val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.fl_main_fragment_container) as NavHostFragment
    navController = navHostFragment.navController
    navController.graph = NavGraph.createNavGraph(navController = navController)
}


internal fun MainActivity.setWindowAttributes() {
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.app_container)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
}


internal fun MainActivity.navigateToProfileFragment() {
    if (!isNavControllerInitialized()) return

    navController.navigate(NavGraph.nav_routes.profile) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}


internal fun MainActivity.navigateToTransactionsFragment() {
    if (!isNavControllerInitialized()) return

    navController.navigate(NavGraph.nav_routes.transactions) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}


internal fun MainActivity.navigateToHomeFragment() {
    if (!isNavControllerInitialized()) return

    navController.navigate(NavGraph.nav_routes.home) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}


internal fun MainActivity.navigateToStatisticsFragment() {
    if (!isNavControllerInitialized()) return

    navController.navigate(NavGraph.nav_routes.statistics) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}


internal fun MainActivity.initializeViews() {
    tvHomeFragment = findViewById(R.id.tv_home)
    tvTransactionsFragment = findViewById(R.id.tv_transactions_history)
    tvStatisticsFragment = findViewById(R.id.tv_statistics)
    tvProfileFragment = findViewById(R.id.tv_profile)
}


internal fun MainActivity.setOnClickListeners() {
    tvHomeFragment.setOnClickListener {
        navigateToHomeFragment()
    }

    tvTransactionsFragment.setOnClickListener {
        navigateToTransactionsFragment()
    }

    tvStatisticsFragment.setOnClickListener {
        navigateToStatisticsFragment()
    }

    tvProfileFragment.setOnClickListener {
        navigateToProfileFragment()
    }

}