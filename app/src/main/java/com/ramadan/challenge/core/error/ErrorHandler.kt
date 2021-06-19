package com.ramadan.challenge.core.error

interface ErrorHandler {
    fun getError(throwable: Throwable): Failure
}
