package com.ramadan.challenge.data.network.api

import com.ramadan.challenge.data.network.api_response.RestaurantsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareAPI {
    @GET("v2/venues/search")
    fun getRestaurants(@Query("ll", encoded = true) ll: String): Single<RestaurantsResponse>
}
