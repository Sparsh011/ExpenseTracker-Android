package com.sparshchadha.expensetracker.feature.auth.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.otpless.dto.HeadlessRequest
import com.otpless.dto.HeadlessResponse
import com.otpless.main.OtplessManager
import com.otpless.main.OtplessView
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.common.utils.Constants
import com.sparshchadha.expensetracker.common.utils.Utility
import com.sparshchadha.expensetracker.common.utils.showToast
import com.sparshchadha.expensetracker.common.utils.vibrateDevice
import com.sparshchadha.expensetracker.core.domain.Resource
import com.sparshchadha.expensetracker.core.navigation.NavigationProvider
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.UserVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.domain.exceptions.InvalidPhoneException
import com.sparshchadha.expensetracker.feature.auth.ui.client.GoogleSignInUIClient
import com.sparshchadha.expensetracker.feature.auth.ui.compose.screens.LoginScreen
import com.sparshchadha.expensetracker.feature.auth.viewmodel.AuthViewModel
import com.sparshchadha.expensetracker.feature.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val authViewModel by viewModels<AuthViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    private lateinit var loginComposeView: ComposeView

    private lateinit var otplessView: OtplessView
    private var isLoading: Boolean = false


    @Inject
    lateinit var navigationProvider: NavigationProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsUsing(view = view)

        initializeOtpless()

        observePhoneVerification()

        loginComposeView.setContent {
            LoginScreen(
                continueWithPhoneAuth = { phoneNumber ->
                    continuePhoneAuthUsing(phoneNumber)
                },
                startGoogleSignIn = {
                    startGoogleSignIn()
                },
                onLoginSkip = {
                    navigateToHomeScreen()
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
                    navigateToVerifyOtpScreen()
                }

                "ONETAP" -> {
                    val token = response.response?.getString("token") ?: ""
                    authViewModel.validateOtpToken(token)
                }
            }
        } else {
            val error = response.response?.optString("errorMessage")
            Utility.errorLog(error ?: "Unable to get error message")
            requireContext().showToast("Unable To Login, Please Try Again!")
        }
    }

    private fun showLoadingScreen() {
        if (isLoading) return
        isLoading = true
        navigationProvider.showLoadingFragment()
    }

    private fun dismissLoadingScreen() {
        if (!isLoading) return
        isLoading = false
        navigationProvider.dismissLoadingFragment()
    }


    private fun continuePhoneAuthUsing(phoneNumberWithCountryCode: String) {
        try {
            val pair = authViewModel.validateAndGetPhoneAndCC(phoneNumberWithCountryCode, '-')

            if (pair.first.isBlank() || pair.second.isBlank()) {
                requireContext().showToast("Enter a valid phone number")
                return
            }
            authViewModel.setUserPhoneNumber(phoneNumberWithCountryCode)


            val headlessRequest = HeadlessRequest()
            headlessRequest.setPhoneNumber(pair.first, pair.second)

            otplessView.startHeadless(headlessRequest, this::onOtplessResult)
//            showLoadingScreen()
        } catch (e: InvalidPhoneException) {
            requireContext().showToast(e.message ?: "Incorrect phone number.")
            dismissLoadingScreen()
        }

    }

    private fun initializeViewsUsing(view: View) {
        loginComposeView = view.findViewById(R.id.login_compose_view)
    }

    private fun navigateToVerifyOtpScreen() {
        navigationProvider.navigateToVerifyOTPFragment(authViewModel.getUserPhoneNumber())
    }

    private fun navigateToHomeScreen(withUser: UserVerificationResponse? = null) {
        withUser?.let { user ->
            if (user.isVerified != true) {
                Toast.makeText(requireContext(), user.message, Toast.LENGTH_SHORT).show()
                return
            }

            authViewModel.saveAccessToken(user.access ?: "")
            authViewModel.saveRefreshToken(user.refresh ?: "")

            profileViewModel.updateUserName(user.userProfile.name)

            navigationProvider.navigateToMainBottomBarNavigationFragment()
        } ?: run {
            dismissLoadingScreen()
            requireContext().showToast("Error during login, please try again!")
        }
    }

    private fun observePhoneVerification() {
        authViewModel.identityVerificationResponse.asLiveData()
            .observe(viewLifecycleOwner) { response ->
                response?.let {
                    when (it) {
                        is Resource.Success -> {
                            dismissLoadingScreen()
                            navigateToHomeScreen(withUser = it.data)
                        }

                        is Resource.Error -> {
                            dismissLoadingScreen()
                            Utility.errorLog(it.error?.message ?: "Unable to fetch error message.")
                            requireContext().showToast("Unable to login, please try again!")
                            requireContext().vibrateDevice()
                        }

                        is Resource.Loading -> {
                            showLoadingScreen()
                        }
                    }
                }
            }
    }


    private fun startGoogleSignIn() {
        val signInClient = GoogleSignInUIClient(requireContext())

        lifecycleScope.launch {
            signInClient.getCredential(
                onSignInComplete = { googleSignInResult ->
                    if (googleSignInResult.isSuccessful) {
                        authViewModel.validateGoogleIdToken(googleSignInResult.idToken)
                        showLoadingScreen()
                    } else {
                        dismissLoadingScreen()
                        Utility.errorLog(googleSignInResult.errorMessage)
                        requireContext().showToast("Unable to login, please try again.")
                    }
                }
            )
        }
    }
}