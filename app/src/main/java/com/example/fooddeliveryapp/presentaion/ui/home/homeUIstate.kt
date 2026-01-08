package com.example.fooddeliveryapp.presentaion.ui.home

import com.example.fooddeliveryapp.presentation.ui.home.RestaurantUiModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val restaurants: List<RestaurantUiModel> = emptyList()
)
