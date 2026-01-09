package com.example.fooddeliveryapp.data.model.remote


data class OrderFirestoreDto(
    val id: String = "",
    val pickupAddress: String = "",
    val deliveryAddress: String = "",
    val customerName: String = "",
    val amount: Double = 0.0,
    val distance: String = "",
    val status: String = "",
    val timestamp: Long = 0L
)

