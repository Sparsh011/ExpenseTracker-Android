package com.sparshchadha.expensetracker.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.ContinueWithPhoneResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.PhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.RetryPhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.User
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.utils.NetworkHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()

    private val _refreshToken = MutableStateFlow("")
    val refreshToken = _refreshToken.asStateFlow()


    private val _continueWithPhoneResponse =
        MutableStateFlow<NetworkHandler<ContinueWithPhoneResponse>?>(null)
    val continueWithPhoneResponse = _continueWithPhoneResponse.asStateFlow()

    private val _verifyIdentityResponse =
        MutableStateFlow<NetworkHandler<User>?>(null)
    val verifyIdentityResponse = _verifyIdentityResponse.asStateFlow()

    private var userPhoneNumber = ""

    private var orderId = ""

    fun saveAccessToken(token: String) {
        authRepository.saveAccessToken(token)
    }

    fun saveRefreshToken(token: String) {
        authRepository.saveRefreshToken(token)
    }

    fun readAccessToken() {
        _accessToken.value = authRepository.getAccessToken()
    }

    fun readRefreshToken() {
        _refreshToken.value = authRepository.getRefreshToken()
    }

    fun continueWithPhone(phoneNumber: String, otpLength: Int = 6, expiry: Int = 120) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.continueWithPhone(
                request = PhoneAuthRequest(
                    phoneNumber = phoneNumber,
                    otpLength = otpLength,
                    expiry = expiry
                )
            ).collect {
                _continueWithPhoneResponse.value = it
            }
        }
    }

    fun retryPhoneAuth(orderId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.retryPhoneAuth(
                request = RetryPhoneAuthRequest(orderId = orderId)
            ).collect {
                withContext(Dispatchers.Main) {
                    _continueWithPhoneResponse.value = it
                }
            }
        }
    }

    fun verifyOtp(phoneNumber: String, otp: String, orderId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.verifyOtp(
                request = VerifyOtpRequest(
                    phoneNumber = phoneNumber,
                    otp = otp,
                    orderId = orderId
                )
            ).collect {
                withContext(Dispatchers.Main) {
                    _verifyIdentityResponse.value = it
                }
            }
        }
    }

    fun getUserPhoneNumber(): String = this.userPhoneNumber

    fun getOtpServiceOrderId(): String = this.orderId

    fun setUserPhoneNumber(number: String) = run { this.userPhoneNumber = number }

    fun setOtpServiceOrderId(orderId: String) = run { this.orderId = orderId }

}