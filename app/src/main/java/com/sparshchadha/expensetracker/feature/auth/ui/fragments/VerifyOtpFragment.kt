package com.sparshchadha.expensetracker.feature.auth.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.OtpVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.ui.compose.screens.VerifyOtpScreen
import com.sparshchadha.expensetracker.feature.auth.viewmodel.AuthViewModel
import com.sparshchadha.expensetracker.feature.bottom_navigation.MainBottomNavigationBarFragment
import com.sparshchadha.expensetracker.utils.BundleKeys
import com.sparshchadha.expensetracker.utils.Resource
import com.sparshchadha.expensetracker.utils.vibrateDevice
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyOtpFragment : Fragment(R.layout.verify_otp_fragment) {
    private val authViewModel by viewModels<AuthViewModel>()

    private lateinit var verifyOtpComposeView: ComposeView
    private var phoneNumberWithCountryCode = ""
    private var orderId = ""
    private val showLoader = mutableStateOf(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsUsing(view = view)

        setObservers()

        if (authViewModel.getOtpServiceOrderId().isBlank()
            || authViewModel.getUserPhoneNumber().isBlank()
        ) {
            authViewModel.setOtpServiceOrderId(
                arguments?.getString(BundleKeys.OTP_SERVICE_ORDER_ID, "") ?: ""
            )
            authViewModel.setUserPhoneNumber(
                arguments?.getString(BundleKeys.PHONE_NUMBER_KEY, "") ?: ""
            )
        }

        phoneNumberWithCountryCode = authViewModel.getUserPhoneNumber()
        orderId = authViewModel.getOtpServiceOrderId()

        verifyOtpComposeView.setContent {
            VerifyOtpScreen(
                providedPhoneNumber = phoneNumberWithCountryCode,
                onVerify = { otp ->
                    authViewModel.verifyOtp(
                        phoneNumber = phoneNumberWithCountryCode, otp = otp, orderId = orderId
                    )
                },
                onResend = {
                    authViewModel.retryPhoneAuth(orderId = orderId)
                },
                onCancel = {
                    requireActivity().supportFragmentManager.popBackStack()
                },
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
                        is Resource.Success -> {
                            showLoader.value = false
                            orderId = it.data?.orderId ?: ""
                        }

                        is Resource.Error -> {
                            showLoader.value = false
                            Toast.makeText(
                                context,
                                it.error?.message ?: "Unable to login, please try again!",
                                Toast.LENGTH_SHORT
                            ).show()
                            requireContext().vibrateDevice()
                        }

                        is Resource.Loading -> {
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
                    is Resource.Success -> {
                        showLoader.value = false
                        navigateToHomeScreen(withUser = it.data)
                    }

                    is Resource.Error -> {
                        showLoader.value = false
                        Toast.makeText(
                            requireContext(),
                            "Unable to login, please try again!",
                            Toast.LENGTH_LONG
                        ).show()
                        requireContext().vibrateDevice()
                    }

                    is Resource.Loading -> {
                        showLoader.value = true
                    }
                }
            }
        }
    }

    private fun initializeViewsUsing(view: View) {
        verifyOtpComposeView = view.findViewById(R.id.verify_otp_compose_view)
    }

    private fun navigateToHomeScreen(withUser: OtpVerificationResponse?) {

        withUser?.let { user ->
            if (!user.isOTPVerified) {
                Toast.makeText(requireContext(), user.message, Toast.LENGTH_SHORT).show()
                return
            }

            authViewModel.saveAccessToken(user.access)
            authViewModel.saveRefreshToken(user.refresh)

            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in, R.anim.fade_out,
                    R.anim.fade_in, R.anim.slide_out
                )
                .replace(
                    R.id.parent_fragment_container, MainBottomNavigationBarFragment()
                )
                .commit()
        } ?: run {
            Toast.makeText(requireContext(), "Error aagaya", Toast.LENGTH_SHORT).show()
        }
    }
}