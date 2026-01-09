package com.example.fooddeliveryapp.presentation.ui.model

data class CartItemUiModel(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val quantity: Int
)
