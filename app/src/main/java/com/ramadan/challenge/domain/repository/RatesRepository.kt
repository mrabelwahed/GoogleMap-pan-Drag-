package com.ramadan.challenge.domain.repository

import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.domain.entity.Rates
import io.reactivex.Single

interface RatesRepository {
    fun getCurrentRates(): Single<DataState<Rates>>
}
