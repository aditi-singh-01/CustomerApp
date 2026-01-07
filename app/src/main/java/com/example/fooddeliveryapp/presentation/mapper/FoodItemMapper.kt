package com.example.fooddeliveryapp.presentation.mapper

import com.example.fooddeliveryapp.domain.model.FoodMenuModel
import com.example.fooddeliveryapp.presentation.ui.screen.MenuItemUiModel

fun FoodMenuModel.toUiModel() : MenuItemUiModel {
    return MenuItemUiModel(
        id = id,
        name = name,
        description = description,
        price = price,
        rating = rating,
        imageUrl = imageUrl
    )
}