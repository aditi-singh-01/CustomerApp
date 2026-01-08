package com.example.fooddeliveryapp.domain.usecase

import com.example.fooddeliveryapp.data.repository.Repository
import com.example.fooddeliveryapp.domain.model.Restaurant

class GetRestaurantsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants()
    }
}
