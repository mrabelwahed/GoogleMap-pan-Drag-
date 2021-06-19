package com.ramadan.challenge.domain.inteactor

import com.ramadan.challenge.core.common.AppConst.KENYA
import com.ramadan.challenge.core.common.AppConst.NIGERIA
import com.ramadan.challenge.core.common.AppConst.TANZANIA
import com.ramadan.challenge.core.common.AppConst.UGANDA
import com.ramadan.challenge.domain.entity.Rates
import com.ramadan.challenge.domain.repository.RatesRepository
import io.reactivex.Single
import javax.inject.Inject

class GetExchangeRates @Inject constructor(private val repository: RatesRepository) : Usecase<Single<Rates>> {
    override fun execute(): Single<Rates> {
        return repository.getCurrentRates().map {
            Rates(
                nGN = it.ratesObj[NIGERIA],
                kES = it.ratesObj[KENYA],
                uGX = it.ratesObj[UGANDA],
                tZS = it.ratesObj[TANZANIA]
            )
        }
    }
}
