package com.sparshchadha.expensetracker.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.ContinueWithPhoneResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.OtpVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.utils.Resource
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

    private var accessToken = ""

    private var refreshToken = ""

    init {
        initializeTokens()
    }

    private val _continueWithPhoneResponse =
        MutableStateFlow<Resource<ContinueWithPhoneResponse>?>(null)
    val continueWithPhoneResponse = _continueWithPhoneResponse.asStateFlow()

    private val _verifyIdentityResponse =
        MutableStateFlow<Resource<OtpVerificationResponse>?>(null)
    val verifyIdentityResponse = _verifyIdentityResponse.asStateFlow()

    private var userPhoneNumber = ""

    private fun initializeTokens() {
        this.accessToken = authRepository.getAccessToken()
        this.refreshToken = authRepository.getRefreshToken()
    }

    fun saveAccessToken(token: String) {
        authRepository.saveAccessToken(token)
    }

    fun saveRefreshToken(token: String) {
        authRepository.saveRefreshToken(token)
    }

    fun getAccessToken(): String {
        return this.accessToken
    }

    fun getRefreshToken(): String {
        return this.refreshToken
    }

    fun getUserPhoneNumber(): String = this.userPhoneNumber

    fun setUserPhoneNumber(number: String) = run { this.userPhoneNumber = number }

    fun validateToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.validateOtpVerificationToken(
                request = VerifyOtpRequest(
                    token = token
                )
            ).collect {
                withContext(Dispatchers.Main) {
                    _verifyIdentityResponse.value = it
                }
            }
        }
    }

}