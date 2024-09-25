package com.sparshchadha.expensetracker.feature.bottom_navigation

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.home.ui.fragment.HomeFragment
import com.sparshchadha.expensetracker.feature.statistics.StatisticsFragment
import com.sparshchadha.expensetracker.feature.transactions.TransactionsFragment
import com.sparshchadha.expensetracker.core.navigation.ExpenseTrackerNavGraph.BottomBarScreenRoutes
import com.sparshchadha.expensetracker.common.utils.AppColors
import com.sparshchadha.expensetracker.common.utils.Dimensions
import com.sparshchadha.expensetracker.common.utils.Utility.noRippleClickable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainBottomNavigationBarFragment : Fragment(R.layout.fragment_main_bottom_navigation_bar) {
    private lateinit var navController: NavController
    private lateinit var cvHomeIcon: ComposeView
    private lateinit var cvTransactionsIcon: ComposeView
    private lateinit var cvStatisticsIcon: ComposeView
    private var selectedIcon = mutableStateOf(BottomBarScreenNames.HOME)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeNavController()
        initializeViewsUsing(view = view)
        setIconsContent()
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
        cvHomeIcon = view.findViewById(R.id.cv_home_icon)
        cvTransactionsIcon = view.findViewById(R.id.cv_transactions_history_icon)
        cvStatisticsIcon = view.findViewById(R.id.cv_statistics_icon)
    }

    private fun navigateToHomeScreen() {
        navController.navigate(BottomBarScreenRoutes.HOME_SCREEN) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    private fun navigateToTransactionsScreen() {
        navController.navigate(BottomBarScreenRoutes.TRANSACTIONS_SCREEN) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    private fun navigateToStatisticsScreen() {
        navController.navigate(BottomBarScreenRoutes.STATISTICS_SCREEN) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    private fun setIconsContent() {
        cvHomeIcon.setContent {
            Icon(
                painter = painterResource(id = R.drawable.home_icon),
                contentDescription = null,
                tint = if (selectedIcon.value == BottomBarScreenNames.HOME) AppColors.primaryColor else Color.LightGray,
                modifier = Modifier
                    .noRippleClickable {
                        if (selectedIcon.value != BottomBarScreenNames.HOME) {
                            selectedIcon.value = BottomBarScreenNames.HOME
                            navigateToHomeScreen()
                        }
                    }
                    .padding(Dimensions.smallPadding() - Dimensions.extraSmallPadding() - 1.dp)
            )
        }

        cvTransactionsIcon.setContent {
            Icon(
                painter = painterResource(id = R.drawable.transactions_icon),
                contentDescription = null,
                tint = if (selectedIcon.value == BottomBarScreenNames.TRANSACTIONS) AppColors.primaryColor else Color.LightGray,
                modifier = Modifier
                    .noRippleClickable {
                        if (selectedIcon.value != BottomBarScreenNames.TRANSACTIONS) {
                            selectedIcon.value = BottomBarScreenNames.TRANSACTIONS
                            navigateToTransactionsScreen()
                        }
                    }
                    .padding(Dimensions.extraSmallPadding())
            )
        }

        cvStatisticsIcon.setContent {
            Icon(
                painter = painterResource(id = R.drawable.statistics_icon),
                contentDescription = null,
                tint = if (selectedIcon.value == BottomBarScreenNames.STATISTICS) AppColors.primaryColor else Color.LightGray,
                modifier = Modifier
                    .noRippleClickable {
                        if (selectedIcon.value != BottomBarScreenNames.STATISTICS) {
                            selectedIcon.value = BottomBarScreenNames.STATISTICS
                            navigateToStatisticsScreen()
                        }
                    }
                    .padding(Dimensions.extraSmallPadding())
            )
        }
    }
}

private enum class BottomBarScreenNames {
    HOME,
    TRANSACTIONS,
    STATISTICS
}