package com.sparshchadha.expensetracker.activity

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.navigation.NavGraph
import com.sparshchadha.expensetracker.navigation.NavigationProvider

class MainActivity : AppCompatActivity() {
    internal lateinit var tvHomeFragment: TextView
    internal lateinit var tvTransactionsFragment: TextView
    internal lateinit var tvStatisticsFragment: TextView
    internal lateinit var tvProfileFragment: TextView
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setWindowAttributes()
        setNavGraph()
        initializeViews()
        setOnClickListeners()
    }

    fun isNavControllerInitialized(): Boolean {
        // Need to check whether navcontroller is initialized or not here only because
        // this check is only available for the properties that are lexically accessible,
        // i.e. declared in the same type or in one of the outer types, or at top level in the same file.
        return ::navController.isInitialized
    }

    fun Context.getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

}