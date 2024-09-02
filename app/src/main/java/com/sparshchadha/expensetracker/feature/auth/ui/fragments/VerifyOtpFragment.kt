package com.sparshchadha.expensetracker.feature.auth.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.RetryPhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.User
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import com.sparshchadha.expensetracker.feature.auth.ui.compose.screens.VerifyOtpScreen
import com.sparshchadha.expensetracker.feature.auth.viewmodel.AuthViewModel
import com.sparshchadha.expensetracker.feature.bottom_navigation.MainBottomNavigationBarScreen
import com.sparshchadha.expensetracker.utils.NetworkHandler

class VerifyOtpFragment : Fragment(R.layout.verify_otp_fragment) {
    private val authViewModel by activityViewModels<AuthViewModel>()

    private lateinit var verifyOtpComposeView: ComposeView
    private var phoneNumberWithCountryCode = ""
    private var orderId = ""
    private val errorDuringLogin = mutableStateOf<Pair<Boolean, String>?>(null)
    private val showLoader = mutableStateOf(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsUsing(view = view)

        setObservers()

        phoneNumberWithCountryCode = authViewModel.getUserPhoneNumber()
        orderId = authViewModel.getOtpServiceOrderId()

        verifyOtpComposeView.setContent {
            VerifyOtpScreen(
                providedPhoneNumber = phoneNumberWithCountryCode,
                onVerify = { otp ->
                    authViewModel.verifyOtp(
                        VerifyOtpRequest(
                            phoneNumber = phoneNumberWithCountryCode,
                            otp = otp,
                            orderId = orderId
                        )
                    )
                },
                onResend = {
                    authViewModel.retryPhoneAuth(RetryPhoneAuthRequest(orderId = orderId))
                },
                onChangeNumber = {
                    findNavController().popBackStack()
                },
                onCancel = {
                    findNavController().popBackStack()
                },
                loginError = errorDuringLogin,
                showLoader = showLoader.value
            )
        }
    }

    private fun setObservers() {
        observePhoneAuthInitiation()
        observeIdentityVerification()
    }

    private fun observePhoneAuthInitiation() {
        authViewModel.continueWithPhoneResponse.asLiveData()
            .observe(viewLifecycleOwner) { response ->
                response?.let {
                    when (it) {
                        is NetworkHandler.Success -> {
                            showLoader.value = false
                            orderId = it.data?.orderId ?: ""
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

    private fun observeIdentityVerification() {
        authViewModel.verifyIdentityResponse.asLiveData().observe(viewLifecycleOwner) { response ->
            response?.let {
                when (it) {
                    is NetworkHandler.Success -> {
                        showLoader.value = false
                        navigateToHomeScreen(withUser = it.data)
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

    private fun initializeViewsUsing(view: View) {
        verifyOtpComposeView = view.findViewById(R.id.verify_otp_compose_view)
    }


    private fun navigateToHomeScreen(withUser: User?) {
        withUser?.let {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in, R.anim.fade_out,
                    R.anim.fade_in, R.anim.slide_out
                )
                .replace(
                    R.id.parent_fragment_container, MainBottomNavigationBarScreen()
                )
                .commit()
        } ?: run {
            errorDuringLogin.value = Pair(true, "Internal server error, please try again!")
        }
    }
}