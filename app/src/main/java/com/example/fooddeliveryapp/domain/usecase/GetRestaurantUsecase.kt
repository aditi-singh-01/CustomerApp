package com.example.fooddeliveryapp.domain.usecase

import com.example.fooddeliveryapp.domain.repository.RestaurantRepository

class GetRestaurantsUseCase(
    private val repository: RestaurantRepository
) {
    suspend operator fun invoke() = repository.getRestaurants()
}
