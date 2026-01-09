package com.example.fooddeliveryapp.data.model.local

data class CartItem(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val quantity: Int = 1
)
