package com.russellmorris.getlocation.data.repository

import com.russellmorris.getlocation.data.source.CityDataSource
import com.russellmorris.getlocation.domain.entity.CityEntity
import com.russellmorris.getlocation.domain.repository.CityRepository
import io.reactivex.Single

class CityRepositoryImpl constructor(
    private val cityDataSource: CityDataSource
) : CityRepository {
    override fun getCities(
        city: String,
        type: String,
        maxResults: String,
        units: String,
        key: String
    ): Single<List<CityEntity>> {
        return cityDataSource.getCities(city, type, maxResults, units, key)
    }

}