package com.example.fooddeliveryapp.data.repository

import com.example.fooddeliveryapp.data.repository.DummyRestaurantData
import com.example.fooddeliveryapp.domain.model.Restaurant
import com.example.fooddeliveryapp.data.repository.Repository
import kotlinx.coroutines.delay

class RestaurantRepositoryImpl : Repository {

    override suspend fun getRestaurants(): List<Restaurant> {
        delay(500) // simulate network delay
        return DummyRestaurantData.restaurants
    }
}
