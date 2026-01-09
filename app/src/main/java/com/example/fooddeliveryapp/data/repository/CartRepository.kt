package com.example.fooddeliveryapp.data.repository

import com.example.fooddeliveryapp.data.model.local.CartItem
import com.example.fooddeliveryapp.data.source.local.CartDataSource

class CartRepository(
    private val dataSource: CartDataSource
) {
    fun getCartItems() = dataSource.getCartItems()
    fun addItem(item: CartItem) = dataSource.addItem(item)
    fun clearCart() = dataSource.clearCart()
}
