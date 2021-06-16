package com.ramadan.premise.core.common

import com.ramadan.premise.core.error.Failure

sealed class DataState <out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Failure) : DataState<Nothing>()
}