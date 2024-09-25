package com.sparshchadha.expensetracker.feature.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.feature.profile.domain.repository.ProfileRepository
import com.sparshchadha.expensetracker.core.domain.Resource
import com.sparshchadha.expensetracker.feature.profile.domain.model.UserField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _profileUri = MutableStateFlow("")
    val profileUri = _profileUri.asStateFlow()

    private val _expenseBudget = MutableStateFlow(-1.0)
    val expenseBudget = _expenseBudget.asStateFlow()

    private fun getUserProfile() {
        val accessToken = authRepository.getAccessToken()

        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.getUserProfile(access = accessToken).collect {
                withContext(Dispatchers.Main) {
                    _userProfile.value = it
                }
            }
        }
    }

    fun updateUserName(newName: String) {
        val accessToken = authRepository.getAccessToken()

        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.updateUserField(
                userField = UserField.NAME,
                newValue = newName,
                accessToken = accessToken
            ).collectLatest {

            }
        }
    }

    fun updateProfileUri(profileUri: String) {
        val accessToken = authRepository.getAccessToken()

        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.updateUserField(
                userField = UserField.PROFILE_PICTURE,
                newValue = profileUri,
                accessToken = accessToken
            ).collectLatest {

            }
        }
    }

    fun updateExpenseBudget(newBudget: Int) {
        val accessToken = authRepository.getAccessToken()

        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.updateUserField(
                userField = UserField.EXPENSE_BUDGET,
                newValue = newBudget.toString(),
                accessToken = accessToken
            ).collectLatest {

            }
        }
    }

    private fun getUserNameFromLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.readUserName().collect {
                _userName.value = it ?: "Guest"
            }
        }
    }

    private fun getProfileUriFromLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.readProfileUri().collect{
                _profileUri.value = it ?: ""
            }
        }
    }

    private fun getExpenseBudgetFromLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.readExpenseBudget().collect{
                _expenseBudget.value = it?.toDoubleOrNull() ?: 0.0
            }
        }
    }

    init {
        getProfileUriFromLocal()
        getExpenseBudgetFromLocal()
        getUserProfile()
        getUserNameFromLocal()
    }
}