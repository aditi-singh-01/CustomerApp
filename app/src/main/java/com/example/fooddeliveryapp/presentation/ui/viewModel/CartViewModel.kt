package com.example.fooddeliveryapp.presentation.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {

    var cartItemCount by mutableStateOf(0)
        private set

    fun addItem() {
        cartItemCount++
    }

    fun clearCart() {
        cartItemCount = 0
    }
}
