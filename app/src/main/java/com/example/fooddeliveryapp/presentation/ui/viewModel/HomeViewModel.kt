package com.example.fooddeliveryapp.presentation.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.presentation.ui.screen.BottomNavItem
import com.example.fooddeliveryapp.presentation.ui.screen.RestaurantUiModel
import com.example.fooddeliveryapp.presentation.ui.screen.dummyRestaurants

class HomeViewModel : ViewModel() {

    var selectedTab by mutableStateOf<BottomNavItem>(BottomNavItem.Home)
        private set

    val restaurants: List<RestaurantUiModel> = dummyRestaurants

    fun onTabSelected(item: BottomNavItem) {
        selectedTab = item
    }
}
