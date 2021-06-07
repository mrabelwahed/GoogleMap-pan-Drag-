package com.ramadan.premise.core.di

import android.content.Context
import com.ramadan.premise.data.network.api.WeatherAPI
import com.ramadan.premise.data.repository.WeatherRepositoryImpl
import com.ramadan.premise.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun providesWeatherRepository(@ApplicationContext appContext: Context, weatherAPI: WeatherAPI): WeatherRepository {
        return WeatherRepositoryImpl(appContext, weatherAPI)
    }
}
