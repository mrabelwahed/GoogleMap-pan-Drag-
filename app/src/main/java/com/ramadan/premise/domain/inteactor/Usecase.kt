package com.ramadan.premise.domain.inteactor

interface Usecase<P, R> {
    fun execute(param: P): R
}