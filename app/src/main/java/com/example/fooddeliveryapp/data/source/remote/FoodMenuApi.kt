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
                imageUrl = "https://images.unsplash.com/photo-1633945274309-2c16c9682c5b?w=800&h=800&fit=crop&auto=format",
                rating = 4.6
            ),
            FoodItem(
                id = 2,
                name = "Paneer Butter Masala",
                description = "Soft paneer cubes in rich tomato butter gravy",
                price = 180,
                imageUrl = "https://images.unsplash.com/photo-1628294896516-344152572ee3?w=800&h=800&fit=crop&auto=format",
                rating = 4.5
            ),
            FoodItem(
                id = 3,
                name = "Masala Dosa",
                description = "Crispy dosa with spiced potato filling",
                price = 120,
                imageUrl = "https://images.unsplash.com/photo-1626078299003-9a3a6b69c8b3?w=800&h=800&fit=crop&auto=format",
                rating = 4.4
            ),
            FoodItem(
                id = 4,
                name = "Butter Chicken",
                description = "Creamy tomato-based curry with grilled chicken",
                price = 240,
                rating = 4.7,
                imageUrl = "https://images.unsplash.com/photo-1600628422019-7f44e40f4f90?w=800&h=800&fit=crop&auto=format"
            ),
            FoodItem(
                id = 5,
                name = "Veg Thali",
                description = "Assorted Indian curries served with rice and roti",
                price = 200,
                imageUrl = "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=800&h=800&fit=crop&auto=format",
                rating = 4.3
            ),
            FoodItem(
                id = 6,
                name = "Gobi Manchurian",
                description = "Crispy cauliflower tossed in spicy sauce",
                price = 150,
                imageUrl = "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=800&h=800&fit=crop&auto=format",
                rating = 4.2
            )
        )
    }
}
