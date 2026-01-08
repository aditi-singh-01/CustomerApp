package com.example.fooddeliveryapp.data.repository

import com.example.fooddeliveryapp.domain.model.Restaurant

interface Repository {
    suspend fun getRestaurants(): List<Restaurant>
}
