package com.example.fooddeliveryapp.domain.useCase

import com.example.fooddeliveryapp.data.model.local.Restaurant
import com.example.fooddeliveryapp.data.repository.Repository


class GetRestaurantsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants()
    }
}
