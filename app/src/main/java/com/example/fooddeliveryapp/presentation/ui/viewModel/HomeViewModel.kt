package com.example.fooddeliveryapp.presentation.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.domain.useCase.GetRestaurantsUseCase
import com.example.fooddeliveryapp.presentation.ui.mapper.toUiModel

import com.example.fooddeliveryapp.presentation.ui.model.RestaurantUiModel
import com.example.fooddeliveryapp.presentation.ui.screen.BottomNavItem
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class HomeViewModel(
    private val getRestaurantUsecase: GetRestaurantsUseCase
) : ViewModel(),
    ContainerHost<HomeViewModel.State, HomeViewModel.SideEffect> {

    // -------------------- STATE --------------------
    data class State(
        val selectedTab: BottomNavItem = BottomNavItem.Home,
        val isLoading: Boolean = false,
        val restaurants: List<RestaurantUiModel> = emptyList(),
        val error: String? = null
    )

    // -------------------- INTENT --------------------
    sealed class Intent {
        data object Load : Intent()
        data class TabSelected(val tab: BottomNavItem) : Intent()
        data class RestaurantClicked(val name: String) : Intent()
    }

    // -------------------- SIDE EFFECT --------------------
    sealed class SideEffect {
        data class NavigateToMenu(val restaurantName: String) : SideEffect()
    }

    override val container = container<State, SideEffect>(
        initialState = State()
    )

    init {
        onIntent(Intent.Load)
    }

    fun onIntent(intent: Intent) = intent {
        when (intent) {
            Intent.Load -> loadRestaurants()

            is Intent.TabSelected -> {
                reduce { state.copy(selectedTab = intent.tab) }
            }

            is Intent.RestaurantClicked -> {
                postSideEffect(
                    SideEffect.NavigateToMenu(intent.name)
                )
            }
        }
    }

    private suspend fun loadRestaurants() = intent {
        reduce {
            state.copy(isLoading = true, error = null)
        }
        try {
            val restaurants = getRestaurantUsecase()
            reduce {
                state.copy(
                    isLoading = false,
                    restaurants = restaurants.map { it.toUiModel() }
                )
            }
        } catch (e: Exception) {
            reduce {
                state.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun onTabSelected(selectedItem: BottomNavItem) = intent{
        reduce {
            state.copy(selectedTab = selectedItem)
        }
    }
}
