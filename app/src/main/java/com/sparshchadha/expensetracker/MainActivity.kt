package com.sparshchadha.expensetracker

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.sparshchadha.expensetracker.feature.home.HomeFragment
import com.sparshchadha.expensetracker.feature.notifications.NotificationsFragment
import com.sparshchadha.expensetracker.feature.profile.ProfileFragment
import com.sparshchadha.expensetracker.feature.statistics.StatisticsFragment
import com.sparshchadha.expensetracker.feature.transactions.TransactionsFragment
import com.sparshchadha.expensetracker.navigation.NavGraph

class MainActivity : AppCompatActivity() {
    private lateinit var tvHomeFragment: TextView
    private lateinit var tvTransactionsFragment: TextView
    private lateinit var tvStatisticsFragment: TextView
    private lateinit var tvProfileFragment: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fl_main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController


        setInsets()
        initializeViews()
        setOnClickListeners(navController = navController)

        navController.graph = navController.createGraph(
            startDestination = NavGraph.nav_routes.home
        ) {
            fragment<HomeFragment>(NavGraph.nav_routes.home)

            fragment<TransactionsFragment>(NavGraph.nav_routes.transactions)

            fragment<StatisticsFragment>(NavGraph.nav_routes.statistics)

            fragment<ProfileFragment>(NavGraph.nav_routes.profile)

            fragment<NotificationsFragment>(NavGraph.nav_routes.notifications_screen)
        }

    }


    private fun navigateToProfileFragment(navController: NavController) {
        navController.navigate(NavGraph.nav_routes.profile) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    private fun navigateToTransactionsFragment(navController: NavController) {
        navController.navigate(NavGraph.nav_routes.transactions) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    private fun navigateToHomeFragment(navController: NavController) {
        navController.navigate(NavGraph.nav_routes.home) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    private fun navigateToStatisticsFragment(navController: NavController) {
        navController.navigate(NavGraph.nav_routes.statistics) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }


    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.app_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun initializeViews() {
        tvHomeFragment = findViewById(R.id.tv_home)
        tvTransactionsFragment = findViewById(R.id.tv_transactions_history)
        tvStatisticsFragment = findViewById(R.id.tv_statistics)
        tvProfileFragment = findViewById(R.id.tv_profile)
    }


    private fun setOnClickListeners(navController: NavController) {
        tvHomeFragment.setOnClickListener {
            navigateToHomeFragment(navController = navController)
        }

        tvTransactionsFragment.setOnClickListener {
            navigateToTransactionsFragment(navController = navController)
        }

        tvStatisticsFragment.setOnClickListener {
            navigateToStatisticsFragment(navController = navController)
        }

        tvProfileFragment.setOnClickListener {
            navigateToProfileFragment(navController = navController)
        }

    }
}