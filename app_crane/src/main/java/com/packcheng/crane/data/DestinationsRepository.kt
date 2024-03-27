package com.packcheng.crane.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DestinationsRepository @Inject constructor(
    private val destinationsLocalDataSource: DestinationsLocalDataSource
) {
    val destinations: List<ExploreModel> = destinationsLocalDataSource.craneDestinations
    val hotels: List<ExploreModel> = destinationsLocalDataSource.craneHotels
    val restaurants: List<ExploreModel> = destinationsLocalDataSource.craneRestaurants
    val cities: List<City> = listCities

    fun getDestination(cityName: String): City? {
        return cities.firstOrNull {
            it.name == cityName
        }
    }
}