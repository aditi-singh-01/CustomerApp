package com.example.fooddeliveryapp.presentation.ui.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fooddeliveryapp.domain.model.OrderStatus
import com.example.fooddeliveryapp.presentation.ui.screen.destinations.HomeScreenDestination
import com.example.fooddeliveryapp.presentation.ui.viewModel.OrderStatusIntent
import com.example.fooddeliveryapp.presentation.ui.viewModel.OrderStatusSideEffect
import com.example.fooddeliveryapp.presentation.ui.viewModel.OrderStatusViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun OrderStatusScreen(
    orderId: String,
    navigator: DestinationsNavigator,
    viewModel: OrderStatusViewModel = koinViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity
    LaunchedEffect(orderId) {
        viewModel.onIntent(OrderStatusIntent.StartObserving(orderId))
    }
    LaunchedEffect(Unit) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                OrderStatusSideEffect.NavigateHome -> {
                    navigator.navigate(HomeScreenDestination) {
                        popUpTo(NavGraphs.root) { inclusive = true }
                    }
                }
            }
        }
    }
    BackHandler {
        if (state.orderStatus == OrderStatus.DELIVERED) {
            viewModel.onIntent(OrderStatusIntent.ExitOrderStatus)
        } else {
            activity?.finish()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
            return@Box
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = when (state.orderStatus) {
                    OrderStatus.ASSIGNED -> "Your order is confirmed"
                    OrderStatus.ARRIVED -> "Delivery partner has arrived"
                    OrderStatus.PICKED_UP -> "Your order is picked up"
                    OrderStatus.DELIVERED -> "Your order is delivered"
                    null -> "Fetching order status"
                },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            if (state.orderStatus == OrderStatus.DELIVERED) {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        viewModel.onIntent(OrderStatusIntent.ExitOrderStatus)
                    }
                ) {
                    Text("Go to Home")
                }
            }
        }
    }
}
