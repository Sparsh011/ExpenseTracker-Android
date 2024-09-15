package com.sparshchadha.expensetracker.feature.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.domain.repository.ProfileRepository
import com.sparshchadha.expensetracker.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _userProfile = MutableStateFlow<Resource<UserProfile>?>(null)
    val userProfile = _userProfile.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private fun getUserProfile() {
        val accessToken = authRepository.getAccessToken()

        if (accessToken.isBlank()) {
            // let user login
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.getUserProfile(access = accessToken).collect {
                withContext(Dispatchers.Main) {
                    _userProfile.value = it
                }
            }
        }
    }

    private fun updateUserName(newName: String) {
        val accessToken = authRepository.getAccessToken()
        if (accessToken.isBlank()) {
            // let user login
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.saveUserName(
                newName = newName,
                access = accessToken
            ).collect {

            }
        }
    }

    init {
        getUserProfile()
    }
}