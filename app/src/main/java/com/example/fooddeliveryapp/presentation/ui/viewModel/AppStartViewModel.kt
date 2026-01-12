package com.example.fooddeliveryapp.presentation.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeliveryapp.data.source.local.OrderLocalStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AppStartState(
    val isLoading: Boolean = true
)

sealed interface AppStartIntent {
    data object AppLaunched : AppStartIntent
}

sealed interface AppStartSideEffect {
    data object NavigateHome : AppStartSideEffect
    data class NavigateOrderStatus(val orderId: String) : AppStartSideEffect
}

class AppStartViewModel(
    private val orderLocalStore: OrderLocalStore
) : ViewModel() {

    private val _state = MutableStateFlow(AppStartState())
    val state: StateFlow<AppStartState> = _state.asStateFlow()

    private val _sideEffect = Channel<AppStartSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onIntent(intent: AppStartIntent) {
        when (intent) {
            AppStartIntent.AppLaunched -> decideStartDestination()
        }
    }

    private fun decideStartDestination() {
        viewModelScope.launch {
            val orderId = orderLocalStore.observeOrderId().first()

            if (orderId != null) {
                _sideEffect.send(
                    AppStartSideEffect.NavigateOrderStatus(orderId)
                )
            } else {
                _sideEffect.send(AppStartSideEffect.NavigateHome)
            }

            _state.value = AppStartState(isLoading = false)
        }
    }
}