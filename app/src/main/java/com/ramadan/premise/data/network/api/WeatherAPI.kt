package com.ramadan.premise.data.network.api

import com.ramadan.premise.data.network.response.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("current")
    fun getCurrentWeatherInfo(@Query("query") query: String): Single<WeatherResponse>
}