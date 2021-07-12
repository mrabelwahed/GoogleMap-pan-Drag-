package com.ramadan.challenge.feature.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ramadan.challenge.core.common.AppConst
import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.domain.entity.Restaurant
import com.ramadan.challenge.domain.error.Failure
import com.ramadan.challenge.domain.inteactor.GetRestaurants
import com.ramadan.challenge.fake.FakeData
import com.ramadan.challenge.feature.restaurants.map.Dto
import com.ramadan.challenge.feature.restaurants.map.RestaurantMapViewModel
import com.ramadan.test_utils.RxSchedulerRule
import com.ramadan.test_utils.mock
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MapRestaurantsViewModelTest {
    @Rule
    @JvmField
    var testSchedulerRule: RxSchedulerRule = RxSchedulerRule()
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var restaurantMapViewModel: RestaurantMapViewModel
    @Mock
    lateinit var getRestaurants: GetRestaurants

    @Mock
    private var currentRestaurantsStateObserver: Observer<DataState<List<Restaurant>>>  = mock()

    @Mock lateinit var dto: Dto
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restaurantMapViewModel = RestaurantMapViewModel(getRestaurants).apply {
                this.restaurantsDataState.observeForever(currentRestaurantsStateObserver)
            }
    }

    @Test
    fun `MapRestaurantsViewModel is ready for test`() {
        assertNotNull(restaurantMapViewModel)
    }

    @Test
    fun `should success when getRestaurants() returns proper data`() {
        // Given
        val data = FakeData.givenData()
        `when`(getRestaurants.execute(dto)).thenReturn(Single.just(DataState.Success(data)))
        // When
        getRestaurants.execute(dto)
        restaurantMapViewModel.changeRatesDataState(data)
        // Then
        verify(currentRestaurantsStateObserver).onChanged(DataState.Success(data))
    }

    @Test(expected = RuntimeException::class)
    fun `should receive error state when getRestaurants() throws Exception `() {
        // given
        `when`(getRestaurants.execute(dto)).thenThrow(RuntimeException())
        // when
        getRestaurants.execute(dto)
        // Then
        verify(currentRestaurantsStateObserver).onChanged(DataState.Error(Failure.Unknown))
    }


}
