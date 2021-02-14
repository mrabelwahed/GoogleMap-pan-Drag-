package com.ramadan.premise.domain.inteactor

import com.ramadan.premise.domain.entity.WeatherInfo
import com.ramadan.premise.domain.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCurrentWeatherInfo @Inject constructor (private val repository: WeatherRepository) : Usecase<String, Single<WeatherInfo>> {
    override fun execute(param: String): Single<WeatherInfo> {
        return repository.getCurrentWeather(param).map {
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
