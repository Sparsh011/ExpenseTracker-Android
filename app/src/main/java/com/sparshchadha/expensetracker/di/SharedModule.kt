package com.sparshchadha.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.sparshchadha.expensetracker.core.network.api.ExpenseTrackerAPI
import com.sparshchadha.expensetracker.core.network.authenticator.AccessTokenInterceptor
import com.sparshchadha.expensetracker.core.storage.datastore.ExpenseTrackerDataStorePreference
import com.sparshchadha.expensetracker.core.storage.room.ExpenseTrackerDatabase
import com.sparshchadha.expensetracker.core.storage.shared_preference.ExpenseTrackerSharedPref
import com.sparshchadha.expensetracker.feature.auth.data.repository.AuthRepositoryImpl
import com.sparshchadha.expensetracker.feature.auth.domain.repository.AuthRepository
import com.sparshchadha.expensetracker.feature.auth.domain.usecase.GetAccessTokenUseCase
import com.sparshchadha.expensetracker.feature.auth.domain.usecase.SaveAccessTokenUseCase
import com.sparshchadha.expensetracker.feature.expense.data.local.room.dao.ExpenseDao
import com.sparshchadha.expensetracker.feature.expense.data.repository.ExpenseRepositoryImpl
import com.sparshchadha.expensetracker.feature.expense.domain.repository.ExpenseRepository
import com.sparshchadha.expensetracker.feature.profile.data.repository.ProfileRepositoryImpl
import com.sparshchadha.expensetracker.feature.profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): ExpenseTrackerDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseTrackerDatabase::class.java,
            ExpenseTrackerDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context,
        accessTokenInterceptor: AccessTokenInterceptor,
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(ChuckerInterceptor(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPreference(
        @ApplicationContext context: Context,
    ): ExpenseTrackerSharedPref {
        return ExpenseTrackerSharedPref(context = context)
    }

    @Singleton
    @Provides
    fun provideApiService(
        okHttpClient: OkHttpClient
    ): ExpenseTrackerAPI {
        return Retrofit.Builder()
            .baseUrl(ExpenseTrackerAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ExpenseTrackerAPI::class.java)
    }
    @Singleton
    @Provides
    fun provideDataStorePreference(@ApplicationContext context: Context): ExpenseTrackerDataStorePreference =
        ExpenseTrackerDataStorePreference(context)

    @Singleton
    @Provides
    fun provideAuthRepository(
        sharedPref: ExpenseTrackerSharedPref,
        api: ExpenseTrackerAPI,
        profileRepository: ProfileRepository
    ): AuthRepository {
        return AuthRepositoryImpl(sharedPref = sharedPref, api = api, profileRepository = profileRepository)
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        api: ExpenseTrackerAPI,
        dataStorePreference: ExpenseTrackerDataStorePreference
    ): ProfileRepository {
        return ProfileRepositoryImpl(api = api, dataStorePreference = dataStorePreference)
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

    @Singleton
    @Provides
    fun provideAccessTokenInterceptor(sharedPref: ExpenseTrackerSharedPref): AccessTokenInterceptor {
        return AccessTokenInterceptor(sharedPref = sharedPref)
    }

    @Singleton
    @Provides
    fun provideExpenseDao(
        database: ExpenseTrackerDatabase
    ): ExpenseDao {
        return database.expenseDao()
    }

    @Singleton
    @Provides
    fun provideExpenseRepository(
        dataStorePreference: ExpenseTrackerDataStorePreference,
        expenseDao: ExpenseDao,
        sharedPref: ExpenseTrackerSharedPref,
        api: ExpenseTrackerAPI
    ): ExpenseRepository {
        return ExpenseRepositoryImpl(dataStorePreference, expenseDao, sharedPref, api)
    }
}