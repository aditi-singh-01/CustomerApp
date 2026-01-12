package com.example.fooddeliveryapp.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fooddeliveryapp.presentation.ui.screen.destinations.HomeScreenDestination
import com.example.fooddeliveryapp.presentation.ui.screen.destinations.OrderStatusScreenDestination
import com.example.fooddeliveryapp.presentation.ui.viewModel.AppStartIntent
import com.example.fooddeliveryapp.presentation.ui.viewModel.AppStartSideEffect
import com.example.fooddeliveryapp.presentation.ui.viewModel.AppStartViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination(start = true)
@Composable
fun RootScreen(
    navigator: DestinationsNavigator,
    viewModel: AppStartViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onIntent(AppStartIntent.AppLaunched)
    }
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                AppStartSideEffect.NavigateHome -> {
                    navigator.navigate(HomeScreenDestination) {
                        popUpTo(NavGraphs.root) { inclusive = true }
                    }
                }

                is AppStartSideEffect.NavigateOrderStatus -> {
                    navigator.navigate(
                        OrderStatusScreenDestination(effect.orderId)
                    ) {
                        popUpTo(NavGraphs.root) { inclusive = true }
                    }
                }
            }
        }
    }
    if (state.isLoading) {
        SplashScreen()
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
