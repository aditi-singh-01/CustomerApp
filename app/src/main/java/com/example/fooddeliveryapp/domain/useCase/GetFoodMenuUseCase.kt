package com.example.fooddeliveryapp.domain.useCase

import com.example.fooddeliveryapp.domain.model.FoodMenuModel
import com.example.fooddeliveryapp.domain.repository.FoodMenuRepository

class GetFoodMenuUseCase(
    private val repository: FoodMenuRepository
) {
    suspend operator fun invoke() : List<FoodMenuModel>{
        return repository.getMenu()
    }
}