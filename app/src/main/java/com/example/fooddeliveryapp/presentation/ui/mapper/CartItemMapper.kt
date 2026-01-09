package com.example.fooddeliveryapp.presentation.ui.mapper

import com.example.fooddeliveryapp.data.model.local.CartItem
import com.example.fooddeliveryapp.presentation.ui.model.CartItemUiModel

fun CartItem.toUiModel() = CartItemUiModel(
    id = id,
    name = name,
    price = price,
    imageUrl = imageUrl,
    quantity = quantity
)
