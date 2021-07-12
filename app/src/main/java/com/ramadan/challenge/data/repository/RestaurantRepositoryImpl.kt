package com.ramadan.challenge.data.repository

import com.google.android.gms.maps.model.LatLng
import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.data.network.InMemoryCache
import com.ramadan.challenge.data.network.api.FoursquareAPI
import com.ramadan.challenge.domain.entity.Restaurant
import com.ramadan.challenge.domain.repository.RestaurantsRepository
import com.ramadan.challenge.feature.restaurants.map.Dto
import io.reactivex.Single

class RestaurantRepositoryImpl(private val foursquareAPI: FoursquareAPI) : RestaurantsRepository {
    override fun getRestaurants(location: Dto): Single<DataState<List<Restaurant>>> {
        val locationStr = "${location.latLng.latitude} , ${location.latLng.longitude}"
        // get restaurants from cache
        val cache = InMemoryCache.get()
        val filter = ArrayList<Restaurant>()
        cache.forEach {
            val ln = LatLng(it.latitude, it.longitude)
            if (location.bounds.contains(ln))
                filter.add(it)
        }
        if (filter != null && filter.isNotEmpty())
            return Single.just(DataState.Success(filter))

        return foursquareAPI.getRestaurants(locationStr).map {
            val restaurants = ArrayList<Restaurant>()
            it.response.venues.forEach {
                venue ->
                val restaurant = Restaurant(
                    id = venue.id,
                    name = venue.name,
                    latitude = venue.location.lat,
                    longitude = venue.location.lng,
                    city = venue.location.city,
                    address = venue.location.address
                )
                restaurants.add(restaurant)
            }
            InMemoryCache.add(restaurants)
            // cache restaurants
            DataState.Success(restaurants)
        }
    }
}
