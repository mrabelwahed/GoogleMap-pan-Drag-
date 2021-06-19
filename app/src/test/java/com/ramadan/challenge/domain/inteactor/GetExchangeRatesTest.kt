package com.ramadan.challenge.domain.inteactor

import com.ramadan.challenge.core.common.AppConst
import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.core.error.Failure
import com.ramadan.challenge.data.network.response.RatesResponse
import com.ramadan.challenge.domain.entity.Rates
import com.ramadan.challenge.domain.repository.RatesRepository
import com.ramadan.challenge.fake.FakeData
import com.ramadan.test_utils.RxSchedulerRule
import io.reactivex.Single
import io.reactivex.SingleObserver
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit

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
        `when`(repository.getCurrentRates()).thenReturn(Single.just(FakeData.givenData()))
        repository.getCurrentRates().map {
           Rates(
               nGN = it.ratesObj[AppConst.NIGERIA],
               kES = it.ratesObj[AppConst.KENYA],
               uGX = it.ratesObj[AppConst.UGANDA],
               tZS = it.ratesObj[AppConst.TANZANIA]
           )
       }.test().assertValue(FakeData.givenRatesData())
    }

   @Test
    fun `GetExchangeRates should receive error  when  Exception  happens`() {
         val error = Throwable("error ")
        `when`(repository.getCurrentRates()).thenReturn(Single.error(error))
         repository.getCurrentRates().test().assertError(error)
    }


}