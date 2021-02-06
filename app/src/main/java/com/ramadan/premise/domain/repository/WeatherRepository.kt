package com.ramadan.premise.domain.repository

import com.ramadan.premise.data.network.response.WeatherResponse
import com.ramadan.premise.domain.entity.WeatherInfo
import io.reactivex.Single

interface WeatherRepository {
    fun getCurrentWeather(cityName: String): Single<WeatherResponse>
    fun getForecastData(): Single<List<WeatherInfo>>
}
