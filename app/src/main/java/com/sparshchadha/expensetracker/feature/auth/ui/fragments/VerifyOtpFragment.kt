package com.sparshchadha.expensetracker.feature.auth.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.otpless.dto.HeadlessRequest
import com.otpless.dto.HeadlessResponse
import com.otpless.main.OtplessManager
import com.otpless.main.OtplessView
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.ui.screens.xml.FullScreenLoaderFragment
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.UserVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.ui.compose.screens.VerifyOtpScreen
import com.sparshchadha.expensetracker.feature.auth.viewmodel.AuthViewModel
import com.sparshchadha.expensetracker.feature.bottom_navigation.MainBottomNavigationBarFragment
import com.sparshchadha.expensetracker.common.utils.BundleKeys
import com.sparshchadha.expensetracker.common.utils.Constants
import com.sparshchadha.expensetracker.common.utils.Resource
import com.sparshchadha.expensetracker.common.utils.Utility
import com.sparshchadha.expensetracker.common.utils.vibrateDevice
import com.sparshchadha.expensetracker.feature.auth.domain.exceptions.InvalidPhoneException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyOtpFragment : Fragment(R.layout.verify_otp_fragment) {
    private val authViewModel by viewModels<AuthViewModel>()

    private lateinit var verifyOtpComposeView: ComposeView
    private var phoneNumberWithCountryCode = ""

    private lateinit var otplessView: OtplessView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsUsing(view = view)

        observeIdentityVerification()

        initializeOtpless()

        if (authViewModel.getUserPhoneNumber().isBlank()) {
            authViewModel.setUserPhoneNumber(
                arguments?.getString(BundleKeys.PHONE_NUMBER_KEY, "") ?: ""
            )
        }

        phoneNumberWithCountryCode = authViewModel.getUserPhoneNumber()

        verifyOtpComposeView.setContent {
            VerifyOtpScreen(
                providedPhoneNumber = phoneNumberWithCountryCode,
                onVerify = { otp ->
                    verifyOtp(otp)
                },
                onResend = {
                    resendOtp(phoneNumberWithCountryCode)
                },
                onCancel = {
                    dismissLoadingScreen()
                    requireActivity().supportFragmentManager.popBackStack()
                }
            )
        }
    }

    private fun initializeOtpless() {
        otplessView = OtplessManager.getInstance().getOtplessView(requireActivity())
        otplessView.initHeadless(Constants.OTPLESS_APPID)
        otplessView.setHeadlessCallback(this::onOtplessResult)
    }

    private fun onOtplessResult(response: HeadlessResponse) {
        dismissLoadingScreen()
        if (response.statusCode == 200) {
            when (response.responseType) {
                "INITIATE" -> {
                    showToast("OTP sent")
                }
                "ONETAP" -> {
                    val token = response.response?.getString("token") ?: ""
                    authViewModel.validateOtpToken(token)
                }
            }
        } else {
            val error = response.response?.optString("errorMessage")
            Utility.errorLog(error ?: "Unable to get error message")
            showToast("Unable To Login, Please Try Again!")
        }
    }

    private fun verifyOtp(otp: String) {
        try {
            val pair = authViewModel.validateAndGetPhoneAndCC(phoneNumberWithCountryCode, '-')

            if (pair.first.isBlank() || pair.second.isBlank()) {
                showToast("Enter a valid phone number")
                return
            }

            if (!Utility.isOtpValid(otp)){
                showToast("Please enter a valid otp")
                return
            }

            val headlessRequest = HeadlessRequest()
            headlessRequest.setPhoneNumber(pair.first, pair.second)
            headlessRequest.setOtp(otp)

            otplessView.startHeadless(headlessRequest, this::onOtplessResult)
            showLoadingScreen()
        } catch (e: InvalidPhoneException) {
            showToast(e.message ?: "Incorrect phone number.")
            dismissLoadingScreen()
        }
    }

    private fun observeIdentityVerification() {
        authViewModel.identityVerificationResponse.asLiveData().observe(viewLifecycleOwner) { response ->
            response?.let {
                when (it) {
                    is Resource.Success -> {
                        dismissLoadingScreen()
                        navigateToHomeScreen (withUser = it.data)
                    }

                    is Resource.Error -> {
                        dismissLoadingScreen()
                        showToast("Unable to login, please try again!")
                        requireContext().vibrateDevice()
                    }

                    is Resource.Loading -> {
                        showLoadingScreen()
                    }
                }
            }
        }
    }

    private fun initializeViewsUsing(view: View) {
        verifyOtpComposeView = view.findViewById(R.id.verify_otp_compose_view)
    }

    private fun navigateToHomeScreen(withUser: UserVerificationResponse?) {

        withUser?.let { user ->
            if (user.isVerified != true) {
                Toast.makeText(requireContext(), user.message, Toast.LENGTH_SHORT).show()
                return
            }

            authViewModel.saveAccessToken(user.access ?: "")
            authViewModel.saveRefreshToken(user.refresh ?: "")

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
            dismissLoadingScreen()
            showToast("Unable to fetch details, please try again!")
        }
    }

    private fun resendOtp(phoneNumberWithCountryCode: String) {
        try {
            val pair = authViewModel.validateAndGetPhoneAndCC(phoneNumberWithCountryCode, '-')

            if (pair.first.isBlank() || pair.second.isBlank()) {
                showToast("Enter a valid phone number")
                return
            }

            val headlessRequest = HeadlessRequest()
            headlessRequest.setPhoneNumber(pair.first, pair.second)

            otplessView.startHeadless(headlessRequest, this::onOtplessResult)

            showLoadingScreen()
        } catch (e: InvalidPhoneException) {
            showToast("Please enter a correct phone number.")
            dismissLoadingScreen()
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoadingScreen() {
        val loaderFragment = FullScreenLoaderFragment()
        loaderFragment.show(requireActivity().supportFragmentManager, "loader")
    }

    private fun dismissLoadingScreen() {
        val loaderFragment = requireActivity().supportFragmentManager.findFragmentByTag("loader") as? FullScreenLoaderFragment
        loaderFragment?.dismiss()
    }


}