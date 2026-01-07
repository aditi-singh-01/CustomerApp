package com.example.fooddeliveryapp.domain.mapper

import com.example.fooddeliveryapp.data.model.remote.FoodItem
import com.example.fooddeliveryapp.domain.model.FoodMenuModel

fun FoodItem.toDomain(): FoodMenuModel {
    return FoodMenuModel(
        id = id,
        name = name,
        description = description,
        price = price,
        rating = rating,
        imageUrl = imageUrl
    )
}