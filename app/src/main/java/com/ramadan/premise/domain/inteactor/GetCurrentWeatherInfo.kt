package com.ramadan.premise.domain.inteactor

import com.ramadan.premise.data.repository.WeatherRepositoryImpl
import com.ramadan.premise.domain.entity.WeatherInfo
import io.reactivex.Single
import javax.inject.Inject

class GetCurrentWeatherInfo @Inject constructor (private val repository: WeatherRepositoryImpl) : Usecase<String , Single<WeatherInfo>> {
    override fun execute(cityName: String): Single<WeatherInfo> {
       return repository.getCurrentWeather(cityName).map {
         WeatherInfo(
                pressure = it.current.pressure,
                temperature = it.current.temperature,
                humidity = it.current.humidity,
                weatherStatus = it.current.weather_descriptions[0],
                weatherIcon = it.current.weather_icons[0]
            )
        }
    }
}