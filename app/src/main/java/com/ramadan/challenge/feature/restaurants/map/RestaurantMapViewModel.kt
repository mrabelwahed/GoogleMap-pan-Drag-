package com.ramadan.challenge.feature.restaurants.map

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.Marker
import com.ramadan.challenge.core.common.BaseViewModel
import com.ramadan.challenge.core.common.DataState
import com.ramadan.challenge.core.common.DataState.Loading
import com.ramadan.challenge.domain.entity.Restaurant
import com.ramadan.challenge.domain.inteactor.GetRestaurants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RestaurantMapViewModel @Inject constructor(private val getRestaurants: GetRestaurants) : BaseViewModel() {
    private val _restaurantsDataState: MutableLiveData<DataState<List<Restaurant>>> = MutableLiveData()
    val restaurantsDataState: LiveData<DataState<List<Restaurant>>>
        get() = _restaurantsDataState

    val markers = HashMap<Marker, Restaurant>()

    var fragCreated  : Boolean = false

    fun getRestaurants(location: Dto) {
        if (_restaurantsDataState.value != null) return

        _restaurantsDataState.value = Loading

        getRestaurants.execute(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { res -> _restaurantsDataState.value = res }
            .also { compositeDisposable.add(it) }
    }

    fun resetRestaurantsDataState() {
        _restaurantsDataState.value = null
    }

    @VisibleForTesting
    fun changeRatesDataState(data: List<Restaurant>) {
        _restaurantsDataState.value = DataState.Success(data)
    }
}
