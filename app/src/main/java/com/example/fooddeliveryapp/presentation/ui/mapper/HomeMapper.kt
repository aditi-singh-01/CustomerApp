package com.example.fooddeliveryapp.presentation.ui.mapper

import com.example.fooddeliveryapp.data.model.local.Restaurant
import com.example.fooddeliveryapp.presentation.ui.model.RestaurantUiModel

fun Restaurant.toUiModel() = RestaurantUiModel( //It converts a domain model into a UI model.
    name = name,
    rating = rating,
    imageUrl = imageUrl
)
