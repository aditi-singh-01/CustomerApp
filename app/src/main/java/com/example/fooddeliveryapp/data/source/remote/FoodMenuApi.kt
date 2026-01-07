package com.example.fooddeliveryapp.data.source.remote

import com.example.fooddeliveryapp.data.model.remote.FoodItem
import kotlinx.coroutines.delay

class FoodMenuApi {

    suspend fun getMenu(): List<FoodItem> {
        delay(1000)
        return listOf(
            FoodItem(
                id = 1,
                name = "Chicken Biryani",
                description = "Aromatic basmati rice cooked with tender chicken and spices",
                price = 220,
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
                rating = 4.6
            ),
            FoodItem(
                id = 2,
                name = "Paneer Butter Masala",
                description = "Soft paneer cubes in rich tomato butter gravy",
                price = 180,
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
                rating = 4.5
            ),
            FoodItem(
                id = 3,
                name = "Masala Dosa",
                description = "Crispy dosa with spiced potato filling",
                price = 120,
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
                rating = 4.4
            ),
            FoodItem(
                id = 4,
                name = "Butter Chicken",
                description = "Creamy tomato-based curry with grilled chicken",
                price = 240,
                rating = 4.7,
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c"
            ),
            FoodItem(
                id = 5,
                name = "Veg Thali",
                description = "Assorted Indian curries served with rice and roti",
                price = 200,
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
                rating = 4.3
            ),
            FoodItem(
                id = 6,
                name = "Gobi Manchurian",
                description = "Crispy cauliflower tossed in spicy sauce",
                price = 150,
                imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
                rating = 4.2
            )
        )
    }
}
