package com.example.fooddeliveryapp.data.repository

import com.example.fooddeliveryapp.data.mapper.toFirestoreDto
import com.example.fooddeliveryapp.domain.model.Order
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class OrderRepositoryImpl(
    private val firestore: FirebaseFirestore
) {
    suspend fun createOrder(order: Order) {
        firestore.collection("orders")
            .document(order.id)
            .set(order.toFirestoreDto())
            .await()
    }
}
