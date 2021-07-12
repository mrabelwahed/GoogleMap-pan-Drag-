package com.ramadan.challenge.domain.inteactor

import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.domain.repository.RestaurantsRepository
import com.ramadan.challenge.fake.FakeData
import com.ramadan.challenge.feature.restaurants.map.Dto
import io.reactivex.Single
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.MockitoAnnotations

class GetRestaurantsTest {

    @Mock
    lateinit var repository: RestaurantsRepository
    lateinit var getRestaurants : GetRestaurants

    @Mock lateinit var dto: Dto

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getRestaurants = GetRestaurants(repository)

    }

    @Test
    fun `GetRestaurants  is ready for testing`() {
        assertNotNull(getRestaurants)
    }

    @Test
    fun `GetRestaurants should return response when call it `() {
        `when`(repository.getRestaurants(dto)).thenReturn(Single.just(DataState.Success(FakeData.givenData())))
         repository.getRestaurants(dto).test().assertValue(DataState.Success(FakeData.givenData()))
    }

   @Test
    fun `GetRestaurants should receive error  when  Exception  happens`() {
         val error = Throwable("error ")
        `when`(repository.getRestaurants(dto)).thenReturn(Single.error(error))
         repository.getRestaurants(dto).test().assertError(error)
    }


}