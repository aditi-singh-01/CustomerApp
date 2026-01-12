package com.example.fooddeliveryapp.presentation.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.data.model.local.CartItem
import com.example.fooddeliveryapp.data.source.local.OrderLocalStore
import com.example.fooddeliveryapp.domain.model.Order
import com.example.fooddeliveryapp.domain.model.OrderStatus
import com.example.fooddeliveryapp.domain.useCase.AddToCartUseCase
import com.example.fooddeliveryapp.domain.useCase.ClearCartUseCase
import com.example.fooddeliveryapp.domain.useCase.CreateOrderUseCase
import com.example.fooddeliveryapp.domain.useCase.GetCartItemsUseCase
import com.example.fooddeliveryapp.presentation.ui.mapper.toUiModel
import com.example.fooddeliveryapp.presentation.ui.model.CartItemUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CartUiState(
    val items: List<CartItemUiModel> = emptyList(),
    val totalAmount: Int = 0,
    val cartCount: Int = 0
)

sealed interface CartIntent {
    data object LoadCart : CartIntent
    data class AddItem(val item: CartItem) : CartIntent
    data object ClearCart : CartIntent
    data object PlaceOrder : CartIntent
}

sealed interface CartSideEffect {
    data class NavigateToOrderStatus(val orderId: String) : CartSideEffect
}

class CartViewModel(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val orderLocalStore: OrderLocalStore
) : ViewModel() {

    private val _state = MutableStateFlow(CartUiState())
    val state: StateFlow<CartUiState> = _state.asStateFlow()

    private val _sideEffect = Channel<CartSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        onIntent(CartIntent.LoadCart)
    }

    fun onIntent(intent: CartIntent) {
        when (intent) {
            CartIntent.LoadCart -> loadCart()
            is CartIntent.AddItem -> addItem(intent.item)
            CartIntent.ClearCart -> clearCart()
            CartIntent.PlaceOrder -> placeOrder()
        }
    }

    private fun loadCart() {
        val items = getCartItemsUseCase().map { it.toUiModel() }
        val total = items.sumOf { it.price * it.quantity }
        val count = items.sumOf { it.quantity }

        _state.value = CartUiState(
            items = items,
            totalAmount = total,
            cartCount = count
        )
    }

    private fun addItem(item: CartItem) {
        addToCartUseCase(item)
        loadCart()
    }

    private fun clearCart() {
        clearCartUseCase()
        _state.value = CartUiState()
    }

    private fun placeOrder() {
        val orderId = "ORD${(1000..9999).random()}"

        viewModelScope.launch {
            val order = Order(
                id = orderId,
                pickupAddress = "Green glen layout",
                deliveryAddress = "Sarjapur rd.",
                customerName = "Aditi Singh",
                amount = state.value.totalAmount.toDouble(),
                distance = "3.2 km",
                status = OrderStatus.ASSIGNED
            )

            createOrderUseCase(order)
            orderLocalStore.saveOrderId(orderId)
            clearCartUseCase()

            _sideEffect.send(
                CartSideEffect.NavigateToOrderStatus(orderId)
            )
        }
    }
}
