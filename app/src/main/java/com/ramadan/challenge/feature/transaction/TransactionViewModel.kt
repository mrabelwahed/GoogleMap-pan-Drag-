package com.ramadan.challenge.feature.transaction

import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ramadan.challenge.core.common.AppConst.COUNTRY_KENYA_CODE
import com.ramadan.challenge.core.common.AppConst.COUNTRY_NIGERIA_CODE
import com.ramadan.challenge.core.common.AppConst.COUNTRY_TANZANIA_CODE
import com.ramadan.challenge.core.common.AppConst.COUNTRY_UGANDA_CODE
import com.ramadan.challenge.core.common.BaseViewModel
import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.domain.entity.Rates
import com.ramadan.challenge.domain.inteactor.GetExchangeRates
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TransactionViewModel @ViewModelInject constructor(
    private val getExchangeRates: GetExchangeRates
) : BaseViewModel() {
    private val _ratesDataState: MutableLiveData<DataState<Rates>> = MutableLiveData()
    val ratesDataState: LiveData<DataState<Rates>>
        get() = _ratesDataState

     var rates: Rates? = null
     var selectedCountryNameCode: String? = null

    fun getCurrentRates() {
        if (_ratesDataState.value != null) return

        getExchangeRates.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { res -> _ratesDataState.value = res }
            .also { compositeDisposable.add(it) }
    }

    fun resetRatesState() {
        _ratesDataState.value = null
    }

    @VisibleForTesting
    fun changeRatesDataState(data: Rates) {
        _ratesDataState.value = DataState.Success(data)
    }

    fun convertBinaryToDecimal(binaryString: String): Int {
        return if (binaryString.isEmpty())
            0
        else
            Integer.parseInt(binaryString, 2)
    }

    fun isValidToSendMoney(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        amountString: String,
        selectedCountryCode: String?
    ): Boolean {
        if (firstName.isEmpty() ||
            lastName.isEmpty() ||
            amountString.isEmpty() ||
            phoneNumber.isEmpty() ||
            selectedCountryCode == null
        )
            return false
        val phoneNumberLength = phoneNumber.length

        // Only phone numbers Kenya, Nigeria, Tanzania, or Uganda should be allowed.
        if (selectedCountryCode == COUNTRY_KENYA_CODE && phoneNumberLength == KENYA_CODE_LENGTH ||
            selectedCountryCode == COUNTRY_NIGERIA_CODE && phoneNumberLength == NIGERIA_CODE_LENGTH ||
            selectedCountryCode == COUNTRY_TANZANIA_CODE && phoneNumberLength == TANZANIA_CODE_LENGTH ||
            selectedCountryCode == COUNTRY_UGANDA_CODE && phoneNumberLength == UGANDA_CODE_LENGTH
        )
            return true

        return false

    }

    fun calcReceivingMoneyBinary(currentRate: Double?, decimal: Int): String {
        if (currentRate != null) {
            val total = currentRate * decimal
            return Integer.toBinaryString(total.toInt())
        }
        return DEFAULT
    }

    companion object {
        const val DEFAULT = "000000000"
        const val KENYA_CODE_LENGTH = 9
        const val NIGERIA_CODE_LENGTH = 7
        const val TANZANIA_CODE_LENGTH = 9
        const val UGANDA_CODE_LENGTH = 7
    }
}
