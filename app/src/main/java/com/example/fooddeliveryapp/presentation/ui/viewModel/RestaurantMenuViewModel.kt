package com.example.fooddeliveryapp.presentation.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.domain.useCase.GetFoodMenuUseCase
import com.example.fooddeliveryapp.presentation.ui.mapper.toUiModel
import com.example.fooddeliveryapp.presentation.ui.model.MenuItemUiModel
import com.example.fooddeliveryapp.presentation.ui.screen.BottomNavItem
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class RestaurantMenuViewModel(
    private val getFoodMenuUseCase: GetFoodMenuUseCase
) : ViewModel(),
    ContainerHost<RestaurantMenuViewModel.State, RestaurantMenuViewModel.SideEffect> {

    data class State(
        val selectedTab: BottomNavItem = BottomNavItem.Home,
        val isLoading: Boolean = false,
        val menuItems: List<MenuItemUiModel> = emptyList()
    )

    sealed interface MenuIntent {
        data class LoadMenu(val menuUrl: String) : MenuIntent
        data class TabSelected(val tab: BottomNavItem) : MenuIntent
    }

    sealed interface SideEffect {
        data class ShowError(val message: String) : SideEffect
    }

    override val container = container<State, SideEffect>(
        initialState = State()
    )

    fun onIntent(menuIntent: MenuIntent) = intent {
        when (menuIntent) {

            is MenuIntent.LoadMenu -> {
                loadMenu(menuIntent.menuUrl)
            }

            is MenuIntent.TabSelected -> {
                reduce {
                    state.copy(selectedTab = menuIntent.tab)
                }
            }
        }
    }

    private suspend fun loadMenu(menuUrl: String) = intent {
        reduce { state.copy(isLoading = true) }

        runCatching {
            getFoodMenuUseCase(menuUrl)
        }.onSuccess { menu ->
            reduce {
                state.copy(
                    isLoading = false,
                    menuItems = menu.map { it.toUiModel() }
                )
            }
        }.onFailure { throwable ->
            reduce { state.copy(isLoading = false) }
            postSideEffect(
                SideEffect.ShowError(
                    throwable.message ?: "Something went wrong"
                )
            )
        }
    }
}