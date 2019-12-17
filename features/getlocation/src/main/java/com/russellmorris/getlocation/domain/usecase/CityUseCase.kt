package com.russellmorris.getlocation.domain.usecase

import com.russellmorris.getlocation.domain.entity.CityEntity
import com.russellmorris.getlocation.domain.repository.CityRepository
import io.reactivex.Single

class CityUseCase constructor(private val cityRepository: CityRepository) {
    fun getCities(city: String,
                  type: String,
                  maxResults: String,
                  units: String,
                  key: String): Single<List<CityEntity>> =
        cityRepository.getCities(city, type, maxResults, units, key)
}