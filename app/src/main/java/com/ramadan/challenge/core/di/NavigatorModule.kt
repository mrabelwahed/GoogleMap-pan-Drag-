package com.ramadan.challenge.core.di

import com.ramadan.challenge.core.navigator.AppNavigator
import com.ramadan.challenge.core.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigatorModule {
    @Binds
    abstract fun bindAppNavigatpr(appNavigator: AppNavigatorImpl): AppNavigator
}
