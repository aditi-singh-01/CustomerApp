package com.example.fooddeliveryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.fooddeliveryapp.presentation.ui.home.HomeScreen
import com.example.fooddeliveryapp.presentaion.ui.theme.FoodDeliveryAppTheme
import com.example.fooddeliveryapp.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
class MainActivity : ComponentActivity() {

    //  Koin injects ViewModel
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FoodDeliveryAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(viewModel = homeViewModel)
                }
            }
        }
    }
}
