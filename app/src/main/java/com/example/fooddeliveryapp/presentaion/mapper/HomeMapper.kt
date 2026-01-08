package com.example.fooddeliveryapp.presentation.home

import com.example.fooddeliveryapp.domain.model.Restaurant
import com.example.fooddeliveryapp.presentation.ui.home.RestaurantUiModel

fun Restaurant.toUiModel() = RestaurantUiModel( //It converts a domain model into a UI model.
    name = name,
    rating = rating,
    imageUrl = imageUrl
)
