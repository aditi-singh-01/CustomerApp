package com.example.fooddeliveryapp.presentation.ui.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fooddeliveryapp.domain.model.OrderStatus
import com.example.fooddeliveryapp.presentation.ui.screen.destinations.HomeScreenDestination
import com.example.fooddeliveryapp.presentation.ui.viewModel.OrderStatusViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun OrderStatusScreen(
    orderId: String,
    navigator: DestinationsNavigator,
    viewModel: OrderStatusViewModel = koinViewModel()
) {
    val status = viewModel.orderStatus
    val context = LocalContext.current
    val activity = context as? Activity

    LaunchedEffect(orderId) {
        viewModel.startObserving(orderId)
    }

    BackHandler {
        if (status == OrderStatus.DELIVERED) {
            navigator.navigate(HomeScreenDestination) {
                popUpTo(NavGraphs.root) { inclusive = true }
            }
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
                text = when (status) {
                    null -> "Fetching order status"
                    OrderStatus.ASSIGNED -> "Your order is Confirmed"
                    OrderStatus.ARRIVED -> "Delivery Partner has arrived"
                    OrderStatus.PICKED_UP -> "Your order is picked up"
                    OrderStatus.DELIVERED -> "Your order is Delivered"
                },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            if (viewModel.orderStatus == OrderStatus.DELIVERED) {
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    navigator.navigate(HomeScreenDestination) {
                        popUpTo(NavGraphs.root) { inclusive = true }
                    }
                }) {
                    Text("Go to Home")
                }
            }
        }
    }
}
