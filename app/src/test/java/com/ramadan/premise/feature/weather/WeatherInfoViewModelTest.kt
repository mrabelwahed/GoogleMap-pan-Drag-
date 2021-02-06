package com.ramadan.premise.feature.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ramadan.premise.domain.entity.WeatherInfo
import com.ramadan.premise.domain.inteactor.GetCurrentWeatherInfo
import com.ramadan.premise.domain.inteactor.GetForecastData
import com.ramadan.premise.util.DataState
import com.ramadan.test_utils.RxSchedulerRule
import com.ramadan.test_utils.mock
import io.reactivex.Single
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class WeatherInfoViewModelTest{
    @Rule
    @JvmField
    var testSchedulerRule: RxSchedulerRule = RxSchedulerRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var weatherInfoViewModel: WeatherInfoViewModel
    @Mock
    lateinit var getCurrentWeatherInfo:GetCurrentWeatherInfo
    @Mock
    lateinit var getForecastData: GetForecastData
    @Mock
    private var forecastWeatherStateObserver: Observer<DataState<List<WeatherInfo>>> = mock()

    @Mock
    private var currentWeatherStateObserver: Observer<DataState<WeatherInfo>> = mock()

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        weatherInfoViewModel = WeatherInfoViewModel(getCurrentWeatherInfo , getForecastData)
            .apply {
                this.weatherDataState.observeForever(currentWeatherStateObserver)
                this.weatherForecastDataState.observeForever(forecastWeatherStateObserver)
            }
    }

    @Test
    fun `WeatherInfoViewModel is ready for test`() {
        assertNotNull(weatherInfoViewModel)
    }

    @Test
    fun `should success when getCurrentWeather() returns proper data`() {
        // Given
        val data = givenData()
        `when`(getCurrentWeatherInfo.execute(anyString())).thenReturn(Single.just(data))
        // When
        getCurrentWeatherInfo.execute(anyString())
        weatherInfoViewModel.changeWeatherDataState(data)
        // Then
        verify(currentWeatherStateObserver).onChanged(DataState.Success(data))
    }

    @Test(expected = RuntimeException::class)
    fun `should receive error state when getCurrentWeather() throws Exception `() {
        // given
        `when`(getCurrentWeatherInfo.execute(anyString())).thenThrow(RuntimeException())
        // when
        getCurrentWeatherInfo.execute(anyString())
        // Then
        verify(currentWeatherStateObserver).onChanged(DataState.Loading)
        verify(currentWeatherStateObserver).onChanged(DataState.Error(RuntimeException()))
    }

    @Test
    fun `should success when getForecastWeatherData() returns proper data`() {
        // Given
        val data = ArrayList<WeatherInfo>()
        data.add(givenData())
        data.add(givenData())
        data.add(givenData())
        `when`(getForecastData.execute(Unit)).thenReturn(Single.just(data))
        // When
        getForecastData.execute(Unit)
        weatherInfoViewModel.changeForecastDataState(data)
        // Then
        verify(forecastWeatherStateObserver).onChanged(DataState.Success(data))
    }

    private fun givenData() : WeatherInfo{
        return WeatherInfo(
            temperature = 10,
            pressure = 30,
            humidity = 20,
            weatherIcon = "fakeIcons",
            weatherStatus = "fakeStatus"
        )
    }

}