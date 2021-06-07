package com.ramadan.premise.feature.weather

import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramadan.premise.core.common.BaseViewModel
import com.ramadan.premise.domain.entity.WeatherInfo
import com.ramadan.premise.domain.inteactor.GetCurrentWeatherInfo
import com.ramadan.premise.domain.inteactor.GetForecastData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherInfoViewModel @ViewModelInject constructor(
    private val getCurrentWeatherInfo: GetCurrentWeatherInfo,
    private val getForecastData: GetForecastData
) : BaseViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _weatherDataState: MutableLiveData<WeatherInfo> = MutableLiveData()
    val weatherDataState: LiveData<WeatherInfo>
        get() = _weatherDataState

    private val _weatherForecastDataState: MutableLiveData<List<WeatherInfo>> = MutableLiveData()
    val weatherForecastDataState: LiveData<List<WeatherInfo>>
        get() = _weatherForecastDataState

    fun getCurrentWeatherInfo(cityName: String) {
        if (_weatherDataState.value != null) return

        val currentWeatherDisposable = getCurrentWeatherInfo.execute(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res -> _weatherDataState.value = res },
                { error -> handleFailure(getError(error)) }
            )
        compositeDisposable.add(currentWeatherDisposable)
    }

    fun getForecastWeatherData() {
        if (_weatherForecastDataState.value != null) return
        val weatherForecastDataDisposable = getForecastData.execute(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res -> _weatherForecastDataState.value = res },
                { error -> handleFailure(getError(error)) }
            )
        compositeDisposable.add(weatherForecastDataDisposable)
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

    @VisibleForTesting
    fun changeWeatherDataState(data: WeatherInfo) {
        _weatherDataState.value = data
    }

    @VisibleForTesting
    fun changeForecastDataState(data: List<WeatherInfo>) {
        _weatherForecastDataState.value = data
    }
}
