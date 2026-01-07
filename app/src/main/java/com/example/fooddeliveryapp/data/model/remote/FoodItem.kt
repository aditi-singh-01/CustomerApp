package com.example.fooddeliveryapp.data.model.remote

data class FoodItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val rating: Double,
    val imageUrl: String
)
