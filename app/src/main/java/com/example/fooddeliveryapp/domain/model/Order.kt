package com.example.fooddeliveryapp.domain.model

data class Order(
    val id: String,
    val pickupAddress: String,
    val deliveryAddress: String,
    val customerName: String,
    val amount: Double,
    val distance: String,
    val status: OrderStatus,
    val timestamp: Long = System.currentTimeMillis()
)

enum class OrderStatus {
    ASSIGNED,
    ARRIVED,
    PICKED_UP,
    DELIVERED
}
