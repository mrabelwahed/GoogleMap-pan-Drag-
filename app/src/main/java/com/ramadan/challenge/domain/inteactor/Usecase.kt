package com.ramadan.challenge.domain.inteactor

interface Usecase<T, R> {
    fun execute(param: T): R
}
