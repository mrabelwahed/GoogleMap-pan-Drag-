package com.ramadan.challenge.data.network.api

import com.ramadan.challenge.data.network.response.RatesResponse
import io.reactivex.Single
import retrofit2.http.GET

interface RatesAPI {
    @GET("api/latest.json")
    fun getCurrentRates(): Single<RatesResponse>
}
