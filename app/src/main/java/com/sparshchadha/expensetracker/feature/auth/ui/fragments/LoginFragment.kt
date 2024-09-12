package com.sparshchadha.expensetracker.feature.auth.ui.fragments

import android.credentials.GetCredentialException
import android.credentials.GetCredentialRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.otpless.dto.HeadlessRequest
import com.otpless.dto.HeadlessResponse
import com.otpless.main.OtplessManager
import com.otpless.main.OtplessView
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.activity.MainActivity.Companion.OTPLESS_APPID
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.OtpVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.ui.compose.screens.LoginScreen
import com.sparshchadha.expensetracker.feature.auth.viewmodel.AuthViewModel
import com.sparshchadha.expensetracker.feature.bottom_navigation.MainBottomNavigationBarFragment
import com.sparshchadha.expensetracker.utils.BundleKeys
import com.sparshchadha.expensetracker.utils.Resource
import com.sparshchadha.expensetracker.utils.vibrateDevice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {
    private val authViewModel by viewModels<AuthViewModel>()

    private lateinit var loginComposeView: ComposeView
    private val errorDuringLogin = mutableStateOf<Pair<Boolean, String>?>(null)
    private val showLoader = mutableStateOf(false)

    private lateinit var otplessView: OtplessView

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
                showLoader = showLoader.value,
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
        otplessView.initHeadless(OTPLESS_APPID)
        otplessView.setHeadlessCallback(this::onOtplessResult)
    }

    private fun onOtplessResult(response: HeadlessResponse) {
        if (response.statusCode == 200) {
            when (response.responseType) {
                "INITIATE" -> {
                    navigateToVerifyOtpScreen()
                }

                "VERIFY" -> {
                    // notify that verification is completed
                    // and this is notified just before "ONETAP" final response
                }

                "OTP_AUTO_READ" -> {
                    val otp = response.response?.optString("otp")
                }

                "ONETAP" -> {
                    // final response with token
                    val token = response.response?.getString("token") ?: ""
                    authViewModel.validateToken(token)
                }
            }
            val successResponse = response.response
        } else {
            // handle error
            val error = response.response?.optString("errorMessage")
            showToast(error ?: "Unable To Login, Please Try Again!")
        }
    }


    private fun continuePhoneAuthUsing(phoneNumberWithCountryCode: String) {
        if (!isPhoneNumberValid(phoneNumberWithCountryCode)) {
            showToast(message = "Enter a valid phone number!")
            return
        }

        val delimiterIndex = phoneNumberWithCountryCode.indexOf('-')

        if (delimiterIndex + 1 >= phoneNumberWithCountryCode.length) {
            showToast("Enter a valid phone number!")
            return
        }

        authViewModel.setUserPhoneNumber(phoneNumberWithCountryCode)

        val countryCode = phoneNumberWithCountryCode.substring(0, delimiterIndex)
        val phone = phoneNumberWithCountryCode.substring(
            delimiterIndex + 1,
            phoneNumberWithCountryCode.length
        )

        val headlessRequest = HeadlessRequest()
        headlessRequest.setPhoneNumber(countryCode, phone)

        otplessView.startHeadless(headlessRequest, this::onOtplessResult)
    }

    private fun initializeViewsUsing(view: View) {
        loginComposeView = view.findViewById(R.id.login_compose_view)
    }

    private fun navigateToVerifyOtpScreen() {
        val fragment = VerifyOtpFragment()
        val bundle = Bundle()
        bundle.putString(BundleKeys.PHONE_NUMBER_KEY, authViewModel.getUserPhoneNumber())
        fragment.arguments = bundle

        requireActivity().supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in, R.anim.fade_out,
                R.anim.fade_in, R.anim.slide_out
            )
            .replace(
                R.id.parent_fragment_container, fragment
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

    private fun navigateToHomeScreen(withUser: OtpVerificationResponse? = null) {
        withUser?.let { user ->
            if (user.isOTPVerified != true) {
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
            Toast.makeText(
                requireContext(),
                "Error during login, please try again!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observePhoneVerification() {
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


    private fun startGoogleSignIn() {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("804888452348-ad9jeu1262u8b45hv3ifr72gkb3vej6q.apps.googleusercontent.com")
            .setAutoSelectEnabled(true)
            .build()

        val signInWithGoogleOption: GetSignInWithGoogleOption =
            GetSignInWithGoogleOption.Builder("804888452348-ad9jeu1262u8b45hv3ifr72gkb3vej6q.apps.googleusercontent.com")

                .build()

        val request = androidx.credentials.GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val credentialManager = CredentialManager.create(requireContext())
                val result = credentialManager.getCredential(
                    request = request,
                    context = requireContext()
                )

                handleSignIn(result)
            } catch (e: Exception) {
                showToast(e.message ?: "Unable to login")
            }
        }
    }

    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        Log.d("MyTagggg", "handleSignIn: ${googleIdTokenCredential.idToken}")
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("MyTagggg", "Received an invalid google id token response", e)
                    }
                }
                else  {
                    // Catch any unrecognized credential type here.
                    Log.e("MyTagggg", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("MyTagggg", "Unexpected type of credential")
            }
        }
    }
}