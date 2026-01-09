package com.example.fooddeliveryapp.data.source.local

import com.example.fooddeliveryapp.data.model.local.CartItem

class CartDataSource {

    private val cartItems = mutableListOf<CartItem>()

    fun getCartItems(): List<CartItem> = cartItems

    fun addItem(item: CartItem) {
        val existing = cartItems.find { it.id == item.id }
        if (existing != null) {
            val index = cartItems.indexOf(existing)
            cartItems[index] = existing.copy(quantity = existing.quantity + 1)
        } else {
            cartItems.add(item)
        }
    }

    fun clearCart() {
        cartItems.clear()
    }
}
