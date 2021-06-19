package com.ramadan.challenge.domain.inteactor

interface Usecase<R> {
    fun execute(): R
}
