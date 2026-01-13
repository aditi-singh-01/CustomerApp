package com.example.fooddeliveryapp.domain.useCase

import com.example.fooddeliveryapp.data.repository.FoodMenuInfoRepository
import com.example.fooddeliveryapp.domain.model.FoodMenuModel

class GetFoodMenuUseCase(
    private val repository: FoodMenuInfoRepository
) {
    suspend operator fun invoke(menuUrl: String): List<FoodMenuModel> {
        return repository.getMenu(menuUrl)
    }
}