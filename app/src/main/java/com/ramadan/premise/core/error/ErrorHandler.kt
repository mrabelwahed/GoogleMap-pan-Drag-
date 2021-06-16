package com.ramadan.premise.core.error

interface ErrorHandler {
    fun getError(throwable: Throwable): Failure
}
