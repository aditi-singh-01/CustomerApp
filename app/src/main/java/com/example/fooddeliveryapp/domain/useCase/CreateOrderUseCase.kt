package com.example.fooddeliveryapp.domain.useCase

import com.example.fooddeliveryapp.data.repository.OrderRepositoryImpl
import com.example.fooddeliveryapp.data.source.local.OrderLocalStore
import com.example.fooddeliveryapp.domain.model.Order

class CreateOrderUseCase(
    private val orderRepository: OrderRepositoryImpl,
    private val orderLocalStore: OrderLocalStore
) {
    suspend operator fun invoke(order: Order) {
        orderRepository.createOrder(order)
        orderLocalStore.saveOrderId(order.id)
    }
}


