package com.example.fooddeliveryapp.presentation.ui.model


data class MenuItemUiModel(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val rating: Double
)
