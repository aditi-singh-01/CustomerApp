package com.example.fooddeliveryapp.presentation.ui.viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.domain.useCase.GetFoodMenuUseCase
import com.example.fooddeliveryapp.presentation.ui.mapper.toUiModel
import com.example.fooddeliveryapp.presentation.ui.screen.BottomNavItem
import com.example.fooddeliveryapp.presentation.ui.model.MenuItemUiModel
import kotlinx.coroutines.launch

data class RestaurantMenuUiState(
    val selectedTab: BottomNavItem = BottomNavItem.Home,
    val isLoading: Boolean = false,
    val menuItems: List<MenuItemUiModel> = emptyList(),
    val error: String? = null
)


class RestaurantMenuViewModel(
    private val getFoodMenuUseCase: GetFoodMenuUseCase
) : ViewModel(){

    var uiState by mutableStateOf(RestaurantMenuUiState())
        private set

    init {
        loadMenu()
    }

    private fun loadMenu(){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val menu = getFoodMenuUseCase()
                uiState = uiState.copy(
                    isLoading = false,
                    menuItems = menu.map { it.toUiModel() })
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = e.message)

            }
        }
    }

    fun onTabSelected(item: BottomNavItem){
        uiState = uiState.copy(selectedTab = item)
    }

}
