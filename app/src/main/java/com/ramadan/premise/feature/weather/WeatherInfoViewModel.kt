package com.ramadan.premise.feature.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramadan.premise.domain.entity.WeatherInfo
import com.ramadan.premise.domain.inteactor.GetCurrentWeatherInfo
import com.ramadan.premise.domain.inteactor.GetForecastData
import com.ramadan.premise.error.WeatherError
import com.ramadan.premise.util.DataState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class WeatherInfoViewModel @ViewModelInject constructor(
    private val getCurrentWeatherInfo: GetCurrentWeatherInfo,
    private val getForecastData: GetForecastData
) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _weatherDataState: MutableLiveData<DataState<WeatherInfo>> = MutableLiveData()
    val weatherDataState: LiveData<DataState<WeatherInfo>>
        get() = _weatherDataState

    private val _weatherForecastDataState: MutableLiveData<DataState<List<WeatherInfo>>> = MutableLiveData()
    val weatherForecastDataState: LiveData<DataState<List<WeatherInfo>>>
        get() = _weatherForecastDataState

    fun getCurrentWeatherInfo(cityName: String) {
        if (_weatherDataState.value != null) return
        _weatherDataState.value = DataState.Loading
        val currentWeatherDisposable = getCurrentWeatherInfo.execute(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res -> _weatherDataState.value = DataState.Success(res) },
                { error -> _weatherDataState.value = handleError(error) }
            )
        compositeDisposable.add(currentWeatherDisposable)
    }

    fun getForecastWeatherData() {
        if (_weatherForecastDataState.value != null) return
        _weatherForecastDataState.value = DataState.Loading
        val weatherForecastDataDisposable = getForecastData.execute(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res -> _weatherForecastDataState.value = DataState.Success(res) },
                { error -> _weatherForecastDataState.value = handleError(error) }
            )
        compositeDisposable.add(weatherForecastDataDisposable)
    }

    private fun handleError(error: Throwable): DataState.Error {
        return if (error is UnknownHostException)
            DataState.Error(WeatherError.NoInternetConnectionError(error))
        else
            DataState.Error(RuntimeException(error.message))
    }

    fun resetWeatherState() {
        _weatherDataState.value = null
    }

    fun resetForecastState() {
        _weatherForecastDataState.value = null
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
