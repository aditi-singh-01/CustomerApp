package com.example.fooddeliveryapp.domain.useCase

import com.example.fooddeliveryapp.data.repository.OrderRepositoryImpl
import com.example.fooddeliveryapp.domain.model.Order

class CreateOrderUseCase(
    private val orderRepository: OrderRepositoryImpl
) {
    suspend operator fun invoke(order: Order) {
        orderRepository.createOrder(order)
    }
}
