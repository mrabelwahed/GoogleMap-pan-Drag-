package com.ramadan.premise.domain.inteactor

import com.ramadan.premise.domain.entity.WeatherInfo
import com.ramadan.premise.domain.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class GetForecastData @Inject constructor (private val repository: WeatherRepository) : Usecase<Unit, Single<List<WeatherInfo>>> {
    override fun execute(param: Unit): Single<List<WeatherInfo>> {
        return repository.getForecastData()
    }
}
