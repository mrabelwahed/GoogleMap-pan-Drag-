package com.ramadan.challenge.core.di

import com.ramadan.challenge.data.network.api.RatesAPI
import com.ramadan.challenge.data.repository.RatesRepositoryImpl
import com.ramadan.challenge.domain.repository.RatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun providesRatesRepository(ratesAPI: RatesAPI): RatesRepository {
        return RatesRepositoryImpl(ratesAPI)
    }
}
