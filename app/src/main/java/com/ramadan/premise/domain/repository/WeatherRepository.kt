package com.ramadan.premise.domain.repository

import com.ramadan.premise.data.network.response.WeatherResponse
import io.reactivex.Single

interface WeatherRepository {
    fun getCurrentWeather(cityName:String) : Single<WeatherResponse>
}