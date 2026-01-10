package com.example.fooddeliveryapp.domain.useCase

import com.example.fooddeliveryapp.data.model.local.Restaurant
import com.example.fooddeliveryapp.data.repository.RestaurantRepositoryImpl


class GetRestaurantsUseCase(
    private val repository: RestaurantRepositoryImpl
) {
    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants()
    }
}
