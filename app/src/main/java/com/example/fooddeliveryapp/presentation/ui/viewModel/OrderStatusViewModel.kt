package com.example.fooddeliveryapp.presentation.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.data.repository.OrderRepositoryImpl
import com.example.fooddeliveryapp.data.source.local.OrderLocalStore
import com.example.fooddeliveryapp.domain.model.OrderStatus
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

data class OrderStatusState(
    val isLoading: Boolean = true,
    val orderStatus: OrderStatus? = null
)

sealed interface OrderStatusIntent {
    data class StartObserving(val orderId: String) : OrderStatusIntent
    data object ExitOrderStatus : OrderStatusIntent
}

sealed interface OrderStatusSideEffect {
    data object NavigateHome : OrderStatusSideEffect
}

class OrderStatusViewModel(
    private val orderRepository: OrderRepositoryImpl,
    private val orderLocalStore: OrderLocalStore
) : ViewModel(),
    ContainerHost<OrderStatusState, OrderStatusSideEffect> {

    override val container = container<OrderStatusState, OrderStatusSideEffect>(
        initialState = OrderStatusState()
    )

    fun onIntent(intent: OrderStatusIntent) = intent {
        when (intent) {

            is OrderStatusIntent.StartObserving -> {
                orderRepository.observeOrderStatus(intent.orderId)
                    .collectLatest { status ->
                        reduce {
                            state.copy(
                                isLoading = false,
                                orderStatus = status
                            )
                        }
                    }
            }

            OrderStatusIntent.ExitOrderStatus -> {
                orderLocalStore.clearOrder()
                postSideEffect(OrderStatusSideEffect.NavigateHome)
            }
        }
    }
}
