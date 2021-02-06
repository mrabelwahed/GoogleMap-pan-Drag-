package com.ramadan.premise.data.repository

import com.ramadan.premise.data.network.api.WeatherAPI
import com.ramadan.premise.data.network.response.WeatherResponse
import com.ramadan.premise.domain.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherAPI: WeatherAPI) : WeatherRepository {
    override fun getCurrentWeather(cityName: String): Single<WeatherResponse> {
       return weatherAPI.getCurrentWeatherInfo(cityName)
    }
}