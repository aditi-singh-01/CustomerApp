package com.example.fooddeliveryapp.presentation.ui.viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.presentation.ui.screen.BottomNavItem
import com.example.fooddeliveryapp.presentation.ui.screen.MenuItemUiModel
import com.example.fooddeliveryapp.presentation.ui.screen.dummyMenuItems

class RestaurantMenuViewModel : ViewModel() {

    var selectedTab by mutableStateOf<BottomNavItem>(BottomNavItem.Home)
        private set

    val menuItems: List<MenuItemUiModel> = dummyMenuItems

    fun onTabSelected(item: BottomNavItem) {
        selectedTab = item
    }
}
