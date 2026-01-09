package com.example.fooddeliveryapp.data.mapper

import com.example.fooddeliveryapp.data.model.remote.OrderFirestoreDto
import com.example.fooddeliveryapp.domain.model.Order

fun Order.toFirestoreDto(): OrderFirestoreDto {
    return OrderFirestoreDto(
        id = id,
        pickupAddress = pickupAddress,
        deliveryAddress = deliveryAddress,
        customerName = customerName,
        amount = amount,
        distance = distance,
        status = status.name,
        timestamp = timestamp
    )
}
