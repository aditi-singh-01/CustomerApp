package com.example.fooddeliveryapp.presentation.ui.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.data.model.local.CartItem
import com.example.fooddeliveryapp.data.source.local.OrderLocalStore
import com.example.fooddeliveryapp.domain.model.Order
import com.example.fooddeliveryapp.domain.model.OrderStatus
import com.example.fooddeliveryapp.domain.useCase.AddToCartUseCase
import com.example.fooddeliveryapp.domain.useCase.GetCartItemsUseCase
import com.example.fooddeliveryapp.domain.useCase.ClearCartUseCase
import com.example.fooddeliveryapp.domain.useCase.CreateOrderUseCase
import com.example.fooddeliveryapp.presentation.ui.mapper.toUiModel
import kotlinx.coroutines.launch

data class CartUiState(
    val items: List<com.example.fooddeliveryapp.presentation.ui.model.CartItemUiModel> = emptyList(),
    val totalAmount: Int = 0,
    val cartCount: Int = 0
)

class CartViewModel(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val orderLocalStore: OrderLocalStore
) : ViewModel() {

    var uiState by mutableStateOf(CartUiState())
        private set

    init {
        loadCart()
    }

    private fun loadCart() {
        val items = getCartItemsUseCase().map { it.toUiModel() }
        val total = items.sumOf { it.price * it.quantity }
        val count = items.sumOf { it.quantity }

        uiState = CartUiState(items, total, count)
    }

    fun addItem(item: CartItem) {
        addToCartUseCase(item)
        loadCart()
    }

    fun clearCart() {
        clearCartUseCase()
        uiState = CartUiState()
    }

    fun placeOrder(): String {
        val orderId = "ORD${(1000..9999).random()}"
        viewModelScope.launch {
            val order = Order(
                id = orderId,
                pickupAddress = "Green glen layout",
                deliveryAddress = "Sarjapur rd.",
                customerName = "Aditi Singh",
                amount = uiState.totalAmount.toDouble(),
                distance = "3.2 km",
                status = OrderStatus.ASSIGNED
            )
            createOrderUseCase(order)
            orderLocalStore.saveOrderId(orderId)
            clearCartUseCase()
        }
        return orderId
    }

}
