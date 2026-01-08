package com.example.fooddeliveryapp.presentaion.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.domain.usecase.GetRestaurantsUseCase
import com.example.fooddeliveryapp.presentaion.ui.home.HomeUiState
import com.example.fooddeliveryapp.presentation.home.toUiModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container


sealed class HomeIntent {
    object LoadRestaurants : HomeIntent()
}

class HomeViewModel(
    private val getRestaurantsUseCase: GetRestaurantsUseCase
) : ViewModel(), ContainerHost<HomeUiState, Nothing> {

    override val container = container<HomeUiState, Nothing>(
        initialState = HomeUiState()
    )

    init {
        loadRestaurants()
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadRestaurants -> loadRestaurants()
        }
    }

    private fun loadRestaurants() = intent {
        reduce {
            state.copy(isLoading = true)
        }

        val restaurants = getRestaurantsUseCase()
            .map { it.toUiModel() }

        reduce {
            state.copy(
                isLoading = false,
                restaurants = restaurants
            )
        }
    }
}