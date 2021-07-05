package com.ramadan.challenge.core.common

import androidx.lifecycle.ViewModel
import com.ramadan.challenge.domain.error.ErrorHandler
import com.ramadan.challenge.domain.error.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject


open class BaseViewModel @Inject constructor() : ViewModel() {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCleared() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        super.onCleared()
    }
}
