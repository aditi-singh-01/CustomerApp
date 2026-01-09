package com.example.fooddeliveryapp.presentation.ui.screen

import com.example.fooddeliveryapp.presentation.ui.model.RestaurantUiModel

data class HomeUiState(
    val selectedTab: BottomNavItem = BottomNavItem.Home,
    val isLoading: Boolean = false,
    val restaurants: List<RestaurantUiModel> = emptyList(),
    val error: String? = null,
    val cartCount: Int = 0
)
