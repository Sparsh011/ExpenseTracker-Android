package com.sparshchadha.expensetracker.feature.auth.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.ui.compose.screens.LoginScreen
import com.sparshchadha.expensetracker.feature.auth.viewmodel.AuthViewModel
import com.sparshchadha.expensetracker.utils.NetworkHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.login_fragment) {
    private val authViewModel by activityViewModels<AuthViewModel>()

    private lateinit var loginComposeView: ComposeView
    private val errorDuringLogin = mutableStateOf<Pair<Boolean, String>?>(null)
    private val showLoader = mutableStateOf(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsUsing(view = view)

        setObservers()

        loginComposeView.setContent {
            LoginScreen(
                continueWithPhoneAuth = { request ->
                    if (!isPhoneNumberValid(request.phoneNumber)) {
                        showToast(message = "Enter a valid phone number!")
                        return@LoginScreen
                    }
                    authViewModel.continueWithPhone(request = request)
                },
                showLoader = showLoader.value,
                startGoogleSignIn = {

                }
            )
        }
    }

    private fun initializeViewsUsing(view: View) {
        loginComposeView = view.findViewById(R.id.login_compose_view)
    }

    private fun navigateToVerifyOtpScreen(orderId: String) {
        if (orderId.isBlank()) {
            showToast(message = "Internal server error, please try again!")
            return
        }

        requireActivity().supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(
                R.id.parent_fragment_container, VerifyOtpFragment()
            )
            .addToBackStack(null)
            .commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        if (!phoneNumber.startsWith("+")) return false
        if (phoneNumber.isBlank() || phoneNumber.length < 6 || phoneNumber.length > 15) return false
        return true
    }

    private fun setObservers() {
        observePhoneAuthInitiation()
    }

    private fun observePhoneAuthInitiation() {
        lifecycleScope.launch(Dispatchers.Main) {
            authViewModel.continueWithPhoneResponse.asLiveData()
                .observe(viewLifecycleOwner) { response ->
                    response?.let {
                        when (it) {
                            is NetworkHandler.Success -> {
                                showLoader.value = false
                                navigateToVerifyOtpScreen(
                                    orderId = it.data?.orderId ?: ""
                                )
                            }

                            is NetworkHandler.Error -> {
                                showLoader.value = false
                                errorDuringLogin.value =
                                    Pair(
                                        true,
                                        it.error?.message ?: "Unable to login, please try again!"
                                    )
                            }

                            is NetworkHandler.Loading -> {
                                showLoader.value = true
                            }
                        }
                    }
                }
        }
    }
}