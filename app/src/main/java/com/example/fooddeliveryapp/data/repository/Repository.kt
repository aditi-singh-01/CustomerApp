package com.example.fooddeliveryapp.data.repository

import com.example.fooddeliveryapp.data.model.local.Restaurant


interface Repository {
    suspend fun getRestaurants(): List<Restaurant>
}