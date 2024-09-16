package com.sparshchadha.expensetracker.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.UserVerificationResponse
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyGoogleIdTokenRequest
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.VerifyOtpRequest
import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.core.domain.Resource
import com.sparshchadha.expensetracker.feature.auth.domain.usecase.ValidateAndSeparatePhoneAndCCUseCase
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

    private val validateAndSeparatePhoneAndCCUseCase = ValidateAndSeparatePhoneAndCCUseCase()

    init {
        initializeTokens()
    }

    private val _identityVerificationResponse =
        MutableStateFlow<Resource<UserVerificationResponse>?>(null)
    val identityVerificationResponse = _identityVerificationResponse.asStateFlow()

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

    fun validateAndGetPhoneAndCC(phoneNumberWithCountryCode: String, delimiter: Char): Pair<String, String> {
        return validateAndSeparatePhoneAndCCUseCase(phoneNumberWithCountryCode, delimiter)
    }

    fun getUserPhoneNumber(): String = this.userPhoneNumber

    fun setUserPhoneNumber(number: String) = run { this.userPhoneNumber = number }

    fun validateOtpToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.validateOtpVerificationToken(
                request = VerifyOtpRequest(
                    token = token
                )
            ).collect {
                withContext(Dispatchers.Main) {
                    _identityVerificationResponse.value = it
                }
            }
        }
    }

    fun validateGoogleIdToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.validateGoogleIdToken(
                request = VerifyGoogleIdTokenRequest(token)
            ).collect {
                withContext(Dispatchers.Main) {
                    _identityVerificationResponse.value = it
                }
            }
        }
    }

}