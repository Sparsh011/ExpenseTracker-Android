package com.sparshchadha.expensetracker.feature.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.feature.auth.domain.usecase.GetAccessTokenUseCase
import com.sparshchadha.expensetracker.feature.auth.domain.usecase.SaveAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
) : ViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()

    fun saveAccessToken(token: String) {
        saveAccessTokenUseCase(token = token)
    }

    fun readAccessToken() {
        _accessToken.value = getAccessTokenUseCase()
    }
}