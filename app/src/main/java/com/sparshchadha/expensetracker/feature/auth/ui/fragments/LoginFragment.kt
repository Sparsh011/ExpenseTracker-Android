package com.sparshchadha.expensetracker.feature.auth.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.ui.compose.screens.LoginScreen
import com.sparshchadha.expensetracker.feature.bottom_navigation.MainBottomNavigationBarScreen
import com.sparshchadha.expensetracker.utils.BundleKeys

class LoginFragment : Fragment(R.layout.login_fragment) {
    private lateinit var loginComposeView: ComposeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsUsing(view = view)

        loginComposeView.setContent {
            LoginScreen(
                navigateToVerifyOtpScreen = { phoneNumberAndCountryCodePair ->
                    if (isPhoneNumberValid(
                            countryCode = phoneNumberAndCountryCodePair.second,
                            phoneNumber = phoneNumberAndCountryCodePair.first,
                        )
                    ) {
                        navigateToVerifyOtpScreen(
                            phoneNumber = phoneNumberAndCountryCodePair.first,
                            countryCode = phoneNumberAndCountryCodePair.second
                        )
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Enter A Valid Phone Number!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                navigateToHomeScreen = {
                    navigateToHomeScreen()
                }
            )
        }
    }

    private fun initializeViewsUsing(view: View) {
        loginComposeView = view.findViewById(R.id.login_compose_view)
    }

    private fun navigateToVerifyOtpScreen(phoneNumber: String, countryCode: String) {
        val fragment = VerifyOtpFragment()
        val bundle = Bundle()
        bundle.putString(BundleKeys.PHONE_NUMBER_KEY, phoneNumber)
        bundle.putString(BundleKeys.COUNTRY_CODE_KEY, countryCode)
        fragment.arguments = bundle
        fragment.show(requireActivity().supportFragmentManager, "VerifyOtpScreenTag")
    }

    private fun isPhoneNumberValid(countryCode: String, phoneNumber: String): Boolean {
        if (countryCode.isBlank() || countryCode.first() != '+') return false
        if (phoneNumber.isBlank() || phoneNumber.length < 6 || phoneNumber.length > 15 || !phoneNumber.isDigitsOnly()) return false
        return true
    }

    private fun navigateToHomeScreen() {
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
    }
}