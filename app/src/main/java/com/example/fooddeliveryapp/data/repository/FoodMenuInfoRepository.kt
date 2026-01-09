package com.example.fooddeliveryapp.data.repository

import com.example.fooddeliveryapp.data.source.remote.FoodMenuApi
import com.example.fooddeliveryapp.domain.mapper.toDomain
import com.example.fooddeliveryapp.domain.model.FoodMenuModel

class FoodMenuInfoRepository (
    private val api: FoodMenuApi
){
    suspend fun getMenu(): List<FoodMenuModel> {
       return api.getMenu().map { it.toDomain() }
    }

}