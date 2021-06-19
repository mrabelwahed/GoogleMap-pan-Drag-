package com.ramadan.challenge.domain.repository

import com.ramadan.challenge.data.network.response.RatesResponse
import io.reactivex.Single

interface RatesRepository {
    fun getCurrentRates(): Single<RatesResponse>
}
