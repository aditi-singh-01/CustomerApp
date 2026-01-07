package com.example.fooddeliveryapp.domain.repository

import com.example.fooddeliveryapp.domain.model.FoodMenuModel

interface FoodMenuRepository {
    suspend fun getMenu(): List<FoodMenuModel>
}