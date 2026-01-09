package com.example.fooddeliveryapp.domain.useCase

import com.example.fooddeliveryapp.data.model.local.CartItem
import com.example.fooddeliveryapp.data.repository.CartRepository

class GetCartItemsUseCase(
    private val repository: CartRepository
) {
    operator fun invoke() = repository.getCartItems()
}

class AddToCartUseCase(
    private val repository: CartRepository
) {
    operator fun invoke(item: CartItem) = repository.addItem(item)
}

class ClearCartUseCase(
    private val repository: CartRepository
) {
    operator fun invoke() = repository.clearCart()
}
