package com.sparshchadha.expensetracker.di

import android.content.Context
import androidx.room.Room
import com.sparshchadha.expensetracker.core.storage.datastore.ExpenseTrackerDataStorePreference
import com.sparshchadha.expensetracker.core.storage.room.ExpenseTrackerDatabase
import com.sparshchadha.expensetracker.core.storage.shared_preference.ExpenseTrackerSharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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
    fun provideSharedPreference(
        @ApplicationContext context: Context,
    ): ExpenseTrackerSharedPref {
        return ExpenseTrackerSharedPref(context = context)
    }

    @Singleton
    @Provides
    fun provideDataStorePreference(@ApplicationContext context: Context): ExpenseTrackerDataStorePreference =
        ExpenseTrackerDataStorePreference(context)
}