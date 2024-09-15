package com.sparshchadha.expensetracker.feature.profile.domain.repository

import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import com.sparshchadha.expensetracker.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserProfile(access: String): Flow<Resource<UserProfile>>

    suspend fun saveUserName(newName: String, access: String): Flow<Resource<Boolean>>
}