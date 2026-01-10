package com.example.fooddeliveryapp.data.repository


import com.example.fooddeliveryapp.data.model.local.Restaurant
import com.example.fooddeliveryapp.data.source.remote.DummyRestaurantData
import kotlinx.coroutines.delay

class RestaurantRepositoryImpl  {
    suspend fun getRestaurants(): List<Restaurant> {
        delay(500)
        return DummyRestaurantData.restaurants
    }
}