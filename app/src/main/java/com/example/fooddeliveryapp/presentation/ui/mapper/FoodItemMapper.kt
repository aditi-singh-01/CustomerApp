package com.example.fooddeliveryapp.presentation.ui.mapper

import android.util.Log
import com.example.fooddeliveryapp.domain.model.FoodMenuModel
import com.example.fooddeliveryapp.presentation.ui.model.MenuItemUiModel

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