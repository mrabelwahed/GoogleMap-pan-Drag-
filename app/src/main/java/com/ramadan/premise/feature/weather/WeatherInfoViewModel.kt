package com.ramadan.premise.feature.weather

import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramadan.premise.core.common.BaseViewModel
import com.ramadan.premise.core.common.DataState
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
    private val _weatherDataState: MutableLiveData<DataState<WeatherInfo>> = MutableLiveData()
    val weatherDataState: LiveData<DataState<WeatherInfo>>
        get() = _weatherDataState

    private val _weatherForecastDataState: MutableLiveData<DataState<List<WeatherInfo>>> = MutableLiveData()
    val weatherForecastDataState: LiveData<DataState<List<WeatherInfo>>>
        get() = _weatherForecastDataState

    fun getCurrentWeatherInfo(cityName: String) {
        if (_weatherDataState.value != null) return

        getCurrentWeatherInfo.execute(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res -> _weatherDataState.value = DataState.Success(res) },
                { error -> _weatherDataState.value = DataState.Error(getError(error)) }
            )
            .also { compositeDisposable.add(it) }

    }

    fun getForecastWeatherData() {
        if (_weatherForecastDataState.value != null) return
       getForecastData.execute(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res -> _weatherForecastDataState.value = DataState.Success(res) },
                { error -> _weatherForecastDataState.value  = DataState.Error(getError(error)) }
            )
           .also { compositeDisposable.add(it)}

    }

    fun resetWeatherState() {
        _weatherDataState.value = null
    }

    fun resetForecastState() {
        _weatherForecastDataState.value = null
    }



    @VisibleForTesting
    fun changeWeatherDataState(data: WeatherInfo) {
        _weatherDataState.value = DataState.Success(data)
    }

    @VisibleForTesting
    fun changeForecastDataState(data: List<WeatherInfo>) {
        _weatherForecastDataState.value = DataState.Success(data)
    }
}
