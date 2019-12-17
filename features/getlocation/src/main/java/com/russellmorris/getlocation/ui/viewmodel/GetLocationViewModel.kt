package com.russellmorris.getlocation.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.russellmorris.extensions.Resource
import com.russellmorris.extensions.setError
import com.russellmorris.extensions.setLoading
import com.russellmorris.extensions.setSuccess
import com.russellmorris.getlocation.BuildConfig
import com.russellmorris.getlocation.domain.usecase.CityUseCase
import com.russellmorris.getlocation.ui.model.City
import com.russellmorris.getlocation.ui.model.mapToPresentation
import com.russellmorris.presentation.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GetLocationViewModel(private val cityUseCase: CityUseCase) : BaseViewModel() {
    val cities = MutableLiveData<Resource<List<City>>>()
    private val compositeDisposable = CompositeDisposable()

    fun getCities(city: String) =
        compositeDisposable.add(cityUseCase.getCities(city, type, maxResults, units, BuildConfig.WEATHER_API_KEY)
            .doOnSubscribe { cities.setLoading() }
            .subscribeOn(Schedulers.io())
            .map { it.mapToPresentation() }
            .subscribe({ cities.setSuccess(it) }, { cities.setError(it.message) })
        )

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    companion object {
        val type = "like"
        val maxResults = "10"
        val units = "metric"
    }
}