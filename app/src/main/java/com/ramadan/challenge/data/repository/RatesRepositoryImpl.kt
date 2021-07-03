package com.ramadan.challenge.data.repository

import com.ramadan.challenge.core.common.AppConst
import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.data.network.api.RatesAPI
import com.ramadan.challenge.data.network.response.RatesResponse
import com.ramadan.challenge.domain.entity.Rates
import com.ramadan.challenge.domain.error.ErrorHandler
import com.ramadan.challenge.domain.error.Failure
import com.ramadan.challenge.domain.repository.RatesRepository
import io.reactivex.Single
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException

class RatesRepositoryImpl(private val ratesAPI: RatesAPI) : RatesRepository {
    override fun getCurrentRates(): Single<DataState<Rates>> {
        return ratesAPI.getCurrentRates().map{
           DataState.Success(
               Rates(
                  nGN = it.ratesObj[AppConst.NIGERIA],
                  kES = it.ratesObj[AppConst.KENYA],
                  uGX = it.ratesObj[AppConst.UGANDA],
                  tZS = it.ratesObj[AppConst.TANZANIA])
           )
        }
//            .onErrorReturn { throwable ->
//               DataState.Error(getError(throwable))
//            }
    }


}
