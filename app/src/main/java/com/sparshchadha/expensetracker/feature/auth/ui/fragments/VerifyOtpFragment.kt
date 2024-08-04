package com.sparshchadha.expensetracker.feature.auth.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sparshchadha.expensetracker.R
import com.sparshchadha.expensetracker.feature.auth.ui.compose.screens.VerifyOtpScreen
import com.sparshchadha.expensetracker.utils.BundleKeys

class VerifyOtpFragment : BottomSheetDialogFragment(R.layout.verify_otp_fragment) {
    private lateinit var verifyOtpComposeView: ComposeView
    private var phoneNumber = ""
    private var countryCode = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        this.isCancelable = false
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewsUsing(view = view)

        phoneNumber = arguments?.getString(BundleKeys.PHONE_NUMBER_KEY) ?: ""
        countryCode = arguments?.getString(BundleKeys.COUNTRY_CODE_KEY) ?: ""

        verifyOtpComposeView.setContent {
            VerifyOtpScreen(
                providedPhoneNumber = "$countryCode $phoneNumber",
                onVerify = { otp ->
                    // Verify OTP
                },
                onResend = {
                    // Resend OTP
                },
                onChangeNumber = {
                    this.dismiss()
                },
                onCancel = {
                    this.dismiss()
                }
            )
        }
    }

    private fun initializeViewsUsing(view: View) {
        verifyOtpComposeView = view.findViewById(R.id.verify_otp_compose_view)
    }
}