package com.example.fooddeliveryapp.presentation.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.data.source.local.OrderLocalStore
import kotlinx.coroutines.flow.*

class AppStartViewModel(
    private val orderLocalStore: OrderLocalStore
) : ViewModel() {

    val startDestination = orderLocalStore.observeOrderId()
        .map { orderId ->
            when {
                orderId == null -> StartDestination.Home
                else -> StartDestination.OrderStatus(orderId)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StartDestination.Loading
        )
}


sealed class StartDestination {
    object Loading : StartDestination()
    object Home : StartDestination()
    data class OrderStatus(val orderId: String) : StartDestination()
}


