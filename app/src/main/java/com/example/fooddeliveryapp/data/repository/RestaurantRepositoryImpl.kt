package com.example.fooddeliveryapp.data.repository

import com.example.fooddeliveryapp.data.source.DummyRestaurantDataSource
import com.example.fooddeliveryapp.domain.model.RestaurantModelUI
import com.example.fooddeliveryapp.domain.repository.RestaurantRepository
import kotlinx.coroutines.delay

class RestaurantRepositoryImpl : RestaurantRepository {

    override suspend fun getRestaurants(): List<RestaurantModelUI> {
        delay(500) // simulate network delay
        return DummyRestaurantDataSource.getRestaurants()
    }
}
