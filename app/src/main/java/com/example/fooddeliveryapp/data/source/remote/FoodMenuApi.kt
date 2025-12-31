package com.example.fooddeliveryapp.data.source.remote

import com.example.fooddeliveryapp.data.model.remote.FoodItem
import kotlinx.coroutines.delay

class FoodMenuApi {

    suspend fun getMenu(): List<FoodItem> {
        delay(1000)

        return listOf(
            FoodItem(
                id = 1,
                name = "Chicken tikka",
                description = "Chicken tikka",
                price = 110,
                rating = 4.4
            ),
            FoodItem(
                id = 2,
                name = "Chilly Chicken",
                description = "Chilly Chicken",
                price = 140,
                rating = 4.2
            ),
            FoodItem(
                id = 3,
                name = "Chicken 65",
                description = "Chicken 65",
                price = 135,
                rating = 4.3
            ),
            FoodItem(
                id = 4,
                name = "Chicken hara bhara",
                description = "Chicken hara bhara",
                price = 125,
                rating = 4.1
        )
        )
    }
}
