package com.example.fooddeliveryapp.presentation.home

import com.example.fooddeliveryapp.domain.model.RestaurantModelUI

fun RestaurantModelUI.toUiModel() = RestaurantUiModel( //It converts a domain model into a UI model.
    name = name,
    rating = rating,
    imageUrl = imageUrl
)
