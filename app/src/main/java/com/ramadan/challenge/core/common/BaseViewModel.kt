package com.ramadan.challenge.core.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        super.onCleared()
    }
}
