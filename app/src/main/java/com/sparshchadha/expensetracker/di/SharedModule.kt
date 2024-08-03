package com.sparshchadha.expensetracker.di

import android.content.Context
import com.sparshchadha.expensetracker.feature.auth.data.repository.AuthRepositoryImpl
import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.feature.auth.domain.usecase.GetAccessTokenUseCase
import com.sparshchadha.expensetracker.feature.auth.domain.usecase.SaveAccessTokenUseCase
import com.sparshchadha.expensetracker.storage.datastore.ExpenseTrackerDataStorePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

    @Singleton
    @Provides
    fun provideDataStorePreference(@ApplicationContext context: Context): ExpenseTrackerDataStorePreference =
        ExpenseTrackerDataStorePreference(context)

    @Singleton
    @Provides
    fun provideAuthRepository(
        dataStorePreference: ExpenseTrackerDataStorePreference,
    ): AuthRepository {
        return AuthRepositoryImpl(dataStorePreference = dataStorePreference)
    }

    @Singleton
    @Provides
    fun provideGetAccessTokenUseCase(authRepository: AuthRepository): GetAccessTokenUseCase {
        return GetAccessTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideSaveAccessTokenUseCase(authRepository: AuthRepository): SaveAccessTokenUseCase {
        return SaveAccessTokenUseCase(authRepository)
    }
}