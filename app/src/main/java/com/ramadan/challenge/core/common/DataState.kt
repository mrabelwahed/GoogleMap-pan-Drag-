package com.ramadan.challenge.core.common

import com.ramadan.challenge.core.error.Failure

sealed class DataState <out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Failure) : DataState<Nothing>()
}
