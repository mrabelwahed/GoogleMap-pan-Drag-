package com.ramadan.challenge.data.repository

import com.ramadan.challenge.data.network.api.RatesAPI
import com.ramadan.challenge.data.network.response.RatesResponse
import com.ramadan.challenge.domain.repository.RatesRepository
import io.reactivex.Single

class RatesRepositoryImpl(private val ratesAPI: RatesAPI) : RatesRepository {
    override fun getCurrentRates(): Single<RatesResponse> {
        return ratesAPI.getCurrentRates()
    }
}
