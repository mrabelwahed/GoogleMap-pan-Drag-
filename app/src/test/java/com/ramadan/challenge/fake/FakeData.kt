package com.ramadan.challenge.fake

import com.ramadan.challenge.data.network.response.RatesResponse
import com.ramadan.challenge.domain.entity.Rates

object FakeData {
     fun givenData(): RatesResponse {
        val ratesObj = HashMap<String,Double>()
        ratesObj["KES"] = 107.0
        ratesObj["NGN"] = 410.0
        ratesObj["TZS"] = 2319.0
        ratesObj["UGX"] = 3535.0
        return RatesResponse(
            disclaimer = "Usage subject to terms: https://openexchangerates.org/terms",
            license = "https://openexchangerates.org/license",
            timestamp = 1624057210,
            base = "USD",
            ratesObj = ratesObj
        )
    }

     fun givenRatesData(): Rates {
        return Rates(kES = 107.0, tZS = 2319.0,nGN = 410.0,uGX = 3535.0)
    }
}