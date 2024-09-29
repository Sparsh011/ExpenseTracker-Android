package com.sparshchadha.expensetracker.di

import androidx.fragment.app.FragmentActivity
import com.sparshchadha.expensetracker.core.navigation.NavigationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {
    @Provides
    fun provideNavigationProvider(activity: FragmentActivity): NavigationProvider {
        return NavigationProvider(activity)
    }
}