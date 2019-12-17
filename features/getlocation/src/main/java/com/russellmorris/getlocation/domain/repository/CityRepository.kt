package com.russellmorris.getlocation.domain.repository

import com.russellmorris.getlocation.domain.entity.CityEntity
import io.reactivex.Single

interface CityRepository {
    fun getCities(city: String,
                  type: String,
                  maxResults: String,
                  units: String,
                  key: String): Single<List<CityEntity>>
}