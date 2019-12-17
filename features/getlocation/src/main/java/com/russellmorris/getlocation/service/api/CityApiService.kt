package com.russellmorris.getlocation.service.api

import com.russellmorris.getlocation.data.source.CityDataSource
import com.russellmorris.getlocation.domain.entity.CityEntity
import com.russellmorris.getlocation.service.dao.mapToDomain
import io.reactivex.Single

class CityApiService constructor(
    private val api: CityApi
) : CityDataSource {
    override fun getCities(
        city: String,
        type: String,
        maxResults: String,
        units: String,
        key: String
    ): Single<List<CityEntity>> {
        return api.getCities(city, type, maxResults, units, key)
            .map { it.mapToDomain() }
    }

}