package com.ramadan.challenge.domain.inteactor

import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.domain.repository.RatesRepository
import com.ramadan.challenge.fake.FakeData
import io.reactivex.Single
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetExchangeRatesTest {

    @Mock
    lateinit var repository: RatesRepository
    lateinit var getExchangeRates: GetExchangeRates


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getExchangeRates = GetExchangeRates(repository)

    }

    @Test
    fun `GetExchangeRates  is ready for testing`() {
        assertNotNull(getExchangeRates)
    }

    @Test
    fun `GetExchangeRates should return response when call it `() {
        `when`(repository.getCurrentRates()).thenReturn(Single.just(DataState.Success(FakeData.givenRatesData())))
         repository.getCurrentRates().test().assertValue(DataState.Success(FakeData.givenRatesData()))
    }

   @Test
    fun `GetExchangeRates should receive error  when  Exception  happens`() {
         val error = Throwable("error ")
        `when`(repository.getCurrentRates()).thenReturn(Single.error(error))
         repository.getCurrentRates().test().assertError(error)
    }


}