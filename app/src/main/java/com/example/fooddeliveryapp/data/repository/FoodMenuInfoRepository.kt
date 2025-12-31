package com.example.fooddeliveryapp.data.repository

import com.example.fooddeliveryapp.data.model.remote.FoodItem
import com.example.fooddeliveryapp.data.source.remote.FoodMenuApi

class FoodMenuInfoRepository (
    private val api: FoodMenuApi = FoodMenuApi()
) {

    suspend fun fetchMenu(): List<FoodItem> {
        return api.getMenu()
    }
}