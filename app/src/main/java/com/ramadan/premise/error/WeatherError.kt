package com.ramadan.premise.error

sealed class WeatherError : RuntimeException() {
    data class NoInternetConnectionError(val exception: Exception) : WeatherError()
}