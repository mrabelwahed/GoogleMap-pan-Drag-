package com.ramadan.challenge.core.di

import com.ramadan.challenge.data.network.api.FoursquareAPI
import com.ramadan.challenge.data.repository.RestaurantRepositoryImpl
import com.ramadan.challenge.domain.repository.RestaurantsRepository
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
    fun providesRestaurantsRepository(foursquareAPI: FoursquareAPI): RestaurantsRepository {
        return RestaurantRepositoryImpl(foursquareAPI)
    }
}
