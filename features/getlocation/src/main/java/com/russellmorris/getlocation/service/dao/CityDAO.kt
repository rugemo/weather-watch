package com.russellmorris.getlocation.service.dao

import com.russellmorris.getlocation.domain.entity.CityEntity
import com.squareup.moshi.Json

data class CityDAO(
    @field:Json(name = "list") val cityList: List<City>
)

data class City(
    @field:Json(name = "coord") val coord: Coord,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "main") val main: Main,
    @field:Json(name = "sys") val sys: Sys,
    @field:Json(name = "weather") val weatherList: List<Weather>
)

data class Coord(
    @field:Json(name = "lat") val lat: Double,
    @field:Json(name = "lon") val lon: Double
)

data class Main(
    @field:Json(name = "temp") val temp: Double
)

data class Sys(
    @field:Json(name = "country") val country: String
)

data class Weather(
    @field:Json(name = "icon") val icon: String
)

fun CityDAO.mapToDomain(): List<CityEntity> = cityList.map {
        CityEntity(
            it.coord.lat,
            it.coord.lon,
            it.name,
            it.main.temp,
            it.sys.country,
            it.weatherList[0].icon
        )
    }
