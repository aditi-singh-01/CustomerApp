package com.example.fooddeliveryapp.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.orderDataStore by preferencesDataStore(
    name = "order_store"
)

class OrderLocalStore(
    private val context: Context
) {

    private val ORDER_ID = stringPreferencesKey("order_id")

    fun observeOrderId(): Flow<String?> {
        return context.orderDataStore.data.map { prefs ->
            prefs[ORDER_ID]
        }
    }

    suspend fun saveOrderId(orderId: String) {
        context.orderDataStore.edit { prefs ->
            prefs[ORDER_ID] = orderId
        }
    }

    suspend fun clearOrder() {
        context.orderDataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
