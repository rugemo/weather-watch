package com.russellmorris.getlocation.service.api

import com.russellmorris.getlocation.service.dao.CityDAO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApi {
    @GET("find?")
    fun getCities(
        @Query("q") city: String,
        @Query("type") type: String,
        @Query("cnt") maxResults: String,
        @Query("units") units: String,
        @Query("appid") key: String
    ) : Single<CityDAO>
}