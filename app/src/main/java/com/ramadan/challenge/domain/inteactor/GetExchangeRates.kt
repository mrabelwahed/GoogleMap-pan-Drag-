package com.ramadan.challenge.domain.inteactor

import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.domain.entity.Rates
import com.ramadan.challenge.domain.error.ErrorHandler
import com.ramadan.challenge.domain.error.Failure
import com.ramadan.challenge.domain.repository.RatesRepository
import io.reactivex.Single
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject

class GetExchangeRates @Inject constructor(private val repository: RatesRepository) :
    Usecase<Single<DataState<Rates>>> ,ErrorHandler{
    override fun execute(): Single<DataState<Rates>> {
        return repository.getCurrentRates().onErrorReturn { DataState.Error(getError(it)) }
    }

    override fun getError(throwable: Throwable): Failure {
        return when (throwable) {
            is UnknownHostException -> Failure.NetworkConnection
            is HttpException -> {
                when (throwable.code()) {
                    // not found
                    HttpURLConnection.HTTP_NOT_FOUND -> Failure.ServerError.NotFound
                    // access denied
                    HttpURLConnection.HTTP_FORBIDDEN -> Failure.ServerError.AccessDenied
                    // unavailable service
                    HttpURLConnection.HTTP_UNAVAILABLE -> Failure.ServerError.ServiceUnavailable
                    // all the others will be treated as unknown error
                    else -> Failure.Unknown
                }
            }
            else -> Failure.Unknown
        }
    }
}
