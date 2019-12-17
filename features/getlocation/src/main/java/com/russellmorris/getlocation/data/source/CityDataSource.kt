package com.russellmorris.getlocation.data.source

import com.russellmorris.getlocation.domain.entity.CityEntity
import io.reactivex.Single

interface CityDataSource {
    fun getCities(city: String,
                  type: String,
                  maxResults: String,
                  units: String,
                  key: String): Single<List<CityEntity>>
}