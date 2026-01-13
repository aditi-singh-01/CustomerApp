package com.example.fooddeliveryapp.data.repository

import android.util.Log
import com.example.fooddeliveryapp.data.source.remote.FoodMenuApi
import com.example.fooddeliveryapp.domain.mapper.toDomain
import com.example.fooddeliveryapp.domain.model.FoodMenuModel

class FoodMenuInfoRepository(
    private val api: FoodMenuApi
) {
    suspend fun getMenu(menuUrl: String): List<FoodMenuModel> {
        val response = api.getMenu(menuUrl)
        Log.d("MENU", response.toString())

        return api.getMenu(menuUrl)
            .map { it.toDomain() }
    }
}
