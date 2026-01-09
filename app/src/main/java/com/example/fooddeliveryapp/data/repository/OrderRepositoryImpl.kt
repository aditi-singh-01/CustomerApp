package com.example.fooddeliveryapp.data.repository

import com.example.fooddeliveryapp.data.mapper.toFirestoreDto
import com.example.fooddeliveryapp.domain.model.Order
import com.example.fooddeliveryapp.domain.model.OrderStatus
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class OrderRepositoryImpl(
    private val firestore: FirebaseFirestore
) {
    private val ordersCollection = firestore.collection("orders")

    suspend fun createOrder(order: Order) {
        ordersCollection
            .document(order.id)
            .set(order.toFirestoreDto())
            .await()
    }

    fun observeOrderStatus(orderId: String): Flow<OrderStatus> = callbackFlow {
        val listener = ordersCollection
            .document(orderId)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                val statusString = snapshot.getString("status") ?: return@addSnapshotListener
                try {
                    trySend(OrderStatus.valueOf(statusString))
                } catch (_: Exception) {
                }
            }

        awaitClose { listener.remove() }
    }
}
