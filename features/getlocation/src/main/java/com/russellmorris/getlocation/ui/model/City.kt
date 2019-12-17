package com.russellmorris.getlocation.ui.model

import com.russellmorris.getlocation.domain.entity.CityEntity

class City (
    val lat: Double,
    val lon: Double,
    val name: String,
    val temp: Double,
    val countryCode: String,
    val icon: String
)

fun CityEntity.mapToPresentation(): City =
    City(lat, lon, name, temp, countryCode, icon)

fun List<CityEntity>.mapToPresentation(): List<City> =
    map { it.mapToPresentation() }