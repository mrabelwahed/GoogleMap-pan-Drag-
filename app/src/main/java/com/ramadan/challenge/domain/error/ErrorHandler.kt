package com.ramadan.challenge.domain.error

interface ErrorHandler {
    fun getError(throwable: Throwable): Failure
}
