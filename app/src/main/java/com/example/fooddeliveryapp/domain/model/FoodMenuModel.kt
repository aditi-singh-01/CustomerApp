package com.example.fooddeliveryapp.domain.model

data class FoodMenuModel(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val rating: Double,
    val imageUrl: String
)
