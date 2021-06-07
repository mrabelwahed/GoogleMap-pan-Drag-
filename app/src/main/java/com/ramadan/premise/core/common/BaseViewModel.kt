package com.ramadan.premise.core.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ramadan.premise.core.error.ErrorHandler
import com.ramadan.premise.core.error.Failure
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() , ErrorHandler {

    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    override fun getError(throwable: Throwable): Failure {
        return  when(throwable){
            is UnknownHostException -> Failure.NetworkConnection
            is HttpException -> {
                when(throwable.code()){
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


    protected fun handleFailure(failure: Failure) {
        _failure.value = failure
    }
}