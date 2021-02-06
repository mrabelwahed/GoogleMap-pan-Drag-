package com.ramadan.premise.data.network.response

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("weather")
    val data: ArrayList<WeatherResponse>
)
