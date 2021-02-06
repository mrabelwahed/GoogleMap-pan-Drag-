package com.ramadan.premise.feature.weather

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.ramadan.premise.R
import com.ramadan.premise.domain.entity.WeatherInfo
import com.ramadan.premise.error.WeatherError
import com.ramadan.premise.util.AppConst.HUMIDITY
import com.ramadan.premise.util.AppConst.PRESSURE
import com.ramadan.premise.util.AppConst.TEMPREATURE
import com.ramadan.premise.util.AppConst.WEATHER_STATUS
import com.ramadan.premise.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_weather.*


@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    val weatherInfoViewModel: WeatherInfoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        handleGoButtonClick()
        observeCurrentWeatherState()
    }


    private fun handleGoButtonClick() {
        goButton.setOnClickListener {
            val cityName = cityNameFiled.text.toString()
            if (cityName.isEmpty())
                displayError(getString(R.string.city_name_hint))
            else{
                hideKeyboardFrom(this , it)
                weatherInfoViewModel.resetWeatherState()
                weatherInfoViewModel.getCurrentWeatherInfo(cityNameFiled.text.toString())
            }
        }
    }

    private fun observeCurrentWeatherState() {
        weatherInfoViewModel.weatherDataState.observe(this, Observer {
            when (it) {
                is DataState.Success<WeatherInfo> -> {
                    handleLoading(false)
                    setCurrentWeatherInfo(it.data)
                }

                is DataState.Error -> {
                    handleLoading(false)
                    if (it.exception is WeatherError.NoInternetConnectionError)
                        displayError(getString(R.string.no_internet_connection))
                    else
                        displayError(it.exception.message)
                }
                is DataState.Loading -> {
                    handleLoading(true)
                }
            }
        })
    }

    private fun setCurrentWeatherInfo(data: WeatherInfo) {
        temperatureTextView.text = TEMPREATURE.plus(data.temperature.toString())
        humidityTextView.text = HUMIDITY.plus(data.humidity.toString())
        pressureTextView.text = PRESSURE.plus(data.pressure.toString())
        weatherStatusTextView.text = WEATHER_STATUS.plus(data.weatherStatus)
        data.weatherIcon?.let {
            Glide.with(applicationContext).load(it).into(weatherIcon)
        }

    }

    private fun handleLoading(isDisplayed: Boolean) {
        //timeCardPullTpRefresh.isRefreshing = isDisplayed
        weatherLoader.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?) {
        message?.let { Snackbar.make(weatherMainView, it, Snackbar.LENGTH_SHORT).show() }
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
