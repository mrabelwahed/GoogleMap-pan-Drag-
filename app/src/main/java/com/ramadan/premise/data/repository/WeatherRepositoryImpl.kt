package com.ramadan.premise.data.repository

import android.content.Context
import com.ramadan.premise.data.network.api.WeatherAPI
import com.ramadan.premise.data.network.response.WeatherResponse
import com.ramadan.premise.domain.entity.WeatherInfo
import com.ramadan.premise.domain.repository.WeatherRepository
import io.reactivex.Single
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class WeatherRepositoryImpl(
    private val context: Context,
    private val weatherAPI: WeatherAPI
) : WeatherRepository {
    override fun getCurrentWeather(cityName: String): Single<WeatherResponse> {
        return weatherAPI.getCurrentWeatherInfo(cityName)
    }

    override fun getForecastData(): Single<List<WeatherInfo>> {
        val weatherInfoList = ArrayList<WeatherInfo>()
        val response = loadJSONFromAssets(context, "data.json") ?: "{}"
        val mainJsonObject = JSONObject(response)
        val forecastData = mainJsonObject.getJSONArray("weather")
        for (i in 0 until forecastData.length()) {
            val humidity = forecastData.getJSONObject(i).optJSONObject("current").optInt("humidity")
            val pressure = forecastData.getJSONObject(i).optJSONObject("current").optInt("pressure")
            val temperature = forecastData.getJSONObject(i).optJSONObject("current").optInt("temperature")
            val weatherStatus = forecastData.getJSONObject(i).optJSONObject("current").optJSONArray("weather_descriptions")[0] as String
            val weatherIcon = forecastData.getJSONObject(i).optJSONObject("current").optJSONArray("weather_icons")[0] as String

            val weatherInfo = WeatherInfo(
                pressure = pressure,
                temperature = temperature,
                humidity = humidity,
                weatherStatus = weatherStatus,
                weatherIcon = weatherIcon
            )
            weatherInfoList.add(weatherInfo)
        }

        return Single.defer {
            Single.just(weatherInfoList)
        }
    }

    private fun loadJSONFromAssets(context: Context, filePath: String): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(filePath)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }
}
