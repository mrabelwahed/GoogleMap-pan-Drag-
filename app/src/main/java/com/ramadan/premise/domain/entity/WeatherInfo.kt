package com.ramadan.premise.domain.entity

data class WeatherInfo(
    val pressure: Int,
    val humidity: Int,
    val weatherStatus: String,
    val temperature: Int,
    val weatherIcon: String
)
