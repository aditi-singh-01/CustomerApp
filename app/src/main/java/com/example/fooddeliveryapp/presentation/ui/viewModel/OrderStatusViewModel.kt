package com.example.fooddeliveryapp.presentation.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.data.repository.OrderRepositoryImpl
import com.example.fooddeliveryapp.data.source.local.OrderLocalStore
import com.example.fooddeliveryapp.domain.model.OrderStatus
import kotlinx.coroutines.launch

class OrderStatusViewModel(
    private val orderRepository: OrderRepositoryImpl,
    private val orderLocalStore: OrderLocalStore
) : ViewModel() {

    var orderStatus by mutableStateOf<OrderStatus?>(null)
        private set

    fun startObserving(orderId: String) {
        viewModelScope.launch {
            orderRepository.observeOrderStatus(orderId).collect { status ->
                orderStatus = status

                if (status == OrderStatus.DELIVERED) {
                    orderLocalStore.clearOrder()
                }
            }
        }
    }
}

