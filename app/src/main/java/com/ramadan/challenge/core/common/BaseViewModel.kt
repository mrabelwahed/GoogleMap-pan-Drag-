package com.ramadan.challenge.core.common

import androidx.lifecycle.ViewModel
import com.ramadan.challenge.domain.error.ErrorHandler
import com.ramadan.challenge.domain.error.Failure
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

//    override fun getError(throwable: Throwable): Failure {
//        return when (throwable) {
//            is UnknownHostException -> Failure.NetworkConnection
//            is HttpException -> {
//                when (throwable.code()) {
//                    // not found
//                    HttpURLConnection.HTTP_NOT_FOUND -> Failure.ServerError.NotFound
//                    // access denied
//                    HttpURLConnection.HTTP_FORBIDDEN -> Failure.ServerError.AccessDenied
//                    // unavailable service
//                    HttpURLConnection.HTTP_UNAVAILABLE -> Failure.ServerError.ServiceUnavailable
//                    // all the others will be treated as unknown error
//                    else -> Failure.Unknown
//                }
//            }
//            else -> Failure.Unknown
//        }
//    }

    override fun onCleared() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        super.onCleared()
    }
}
