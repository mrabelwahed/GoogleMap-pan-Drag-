package com.ramadan.challenge.core.common

import com.ramadan.challenge.domain.error.Failure

sealed class DataState <T> {
//    data class Success<out T>(val data: T) : DataState<T>()
//    data class Error(val exception: Failure) : DataState<Nothing>()

    data class Success<T>(val data: T) : DataState<T>()

    data class Error<T>(val error: Failure) : DataState<T>()
}
