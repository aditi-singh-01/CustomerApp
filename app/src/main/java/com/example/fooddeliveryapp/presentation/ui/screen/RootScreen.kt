package com.example.fooddeliveryapp.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.fooddeliveryapp.presentation.ui.screen.destinations.HomeScreenDestination
import com.example.fooddeliveryapp.presentation.ui.screen.destinations.OrderStatusScreenDestination
import com.example.fooddeliveryapp.presentation.ui.viewModel.AppStartViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.StartDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination(start = true)
@Composable
fun RootScreen(
    navigator: DestinationsNavigator,
    viewModel: AppStartViewModel = koinViewModel()
) {
    val destination by viewModel.startDestination.collectAsState()
    var hasNavigated by remember { mutableStateOf(false) }

    when (destination) {
        StartDestination.Loading -> {
            SplashScreen()
        }

        StartDestination.Home -> {
            LaunchedEffect(Unit) {
                if (hasNavigated) return@LaunchedEffect
                hasNavigated = true

                navigator.navigate(HomeScreenDestination) {
                    popUpTo(NavGraphs.root) { inclusive = true }
                }
            }
        }

        is StartDestination.OrderStatus -> {
            val orderId = (destination as StartDestination.OrderStatus).orderId

            LaunchedEffect(orderId) {
                if (hasNavigated) return@LaunchedEffect
                hasNavigated = true

                navigator.navigate(OrderStatusScreenDestination(orderId)) {
                    popUpTo(NavGraphs.root) { inclusive = true }
                }
            }
        }
    }
}


@Composable
fun SplashScreen() {
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator()
    }
}

