package com.sparshchadha.expensetracker.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.PhoneAuthRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.ContinueWithPhoneResponse
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
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()

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

    fun readAccessToken() {
        _accessToken.value = authRepository.getAccessToken()
    }

    fun continueWithPhone(request: PhoneAuthRequest) {
        userPhoneNumber = request.phoneNumber

        viewModelScope.launch(Dispatchers.IO) {
            authRepository.continueWithPhone(
                request
            ).collect {
                _continueWithPhoneResponse.value = it
                orderId = it.data?.orderId ?: ""
            }
        }
    }

    fun retryPhoneAuth(request: RetryPhoneAuthRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.retryPhoneAuth(request = request).collect {
                _continueWithPhoneResponse.value = it
            }
        }
    }

    fun verifyOtp(request: VerifyOtpRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.verifyOtp(
                request
            ).collect {
                _verifyIdentityResponse.value = it
            }
        }
    }

    fun getUserPhoneNumber(): String = this.userPhoneNumber

    fun getOtpServiceOrderId(): String = this.orderId
}