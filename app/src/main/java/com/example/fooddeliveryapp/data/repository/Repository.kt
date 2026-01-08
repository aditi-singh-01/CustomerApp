package com.example.fooddeliveryapp.domain.repository

import com.example.fooddeliveryapp.domain.model.RestaurantModelUI

interface RestaurantRepository {
    suspend fun getRestaurants(): List<RestaurantModelUI>
}
