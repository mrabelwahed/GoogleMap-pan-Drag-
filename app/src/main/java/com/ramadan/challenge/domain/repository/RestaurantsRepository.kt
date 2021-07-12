package com.ramadan.challenge.domain.repository

import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.domain.entity.Restaurant
import com.ramadan.challenge.feature.restaurants.map.Dto
import io.reactivex.Single

interface RestaurantsRepository {
    fun getRestaurants(location: Dto): Single<DataState<List<Restaurant>>>
}
