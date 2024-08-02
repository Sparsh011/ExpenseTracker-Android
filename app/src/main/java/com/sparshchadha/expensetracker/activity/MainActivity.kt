package com.sparshchadha.expensetracker.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    lateinit var parentNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        lifecycleScope.launch {
//            authViewModel.accessToken
//                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED) // Works the same as repeatOnLifecycle(STARTED)
//                .collect { accessToken ->
//                    if (accessToken.isBlank()) {
//                        showOnboardingScreen()
//                    } else {
//                        setNavGraph()
//                    }
//                }
//        }

        setGlobalNavGraph()
        setWindowAttributes()
    }

}