package com.example.fooddeliveryapp.di

import com.example.fooddeliveryapp.data.repository.CartRepository
import com.example.fooddeliveryapp.data.source.remote.FoodMenuApi
import com.example.fooddeliveryapp.data.repository.FoodMenuInfoRepository
import com.example.fooddeliveryapp.data.source.local.CartDataSource
import com.example.fooddeliveryapp.domain.useCase.AddToCartUseCase
import com.example.fooddeliveryapp.domain.useCase.ClearCartUseCase
import com.example.fooddeliveryapp.domain.useCase.CreateOrderUseCase
import com.example.fooddeliveryapp.domain.useCase.GetCartItemsUseCase
import com.example.fooddeliveryapp.domain.useCase.GetFoodMenuUseCase
import com.example.fooddeliveryapp.presentation.ui.viewModel.CartViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.RestaurantMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.fooddeliveryapp.data.repository.OrderRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore


val appModule = module {

    // API/database
    single { FoodMenuApi() }
    single { CartDataSource() }

    // Repository
    single { FoodMenuInfoRepository(get()) }
    single { CartRepository(get()) }
    single { FirebaseFirestore.getInstance() }
    single { OrderRepositoryImpl(get()) }

    // Use case
    factory {
        GetFoodMenuUseCase(get())
    }
    factory { GetCartItemsUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { ClearCartUseCase(get()) }
    factory { CreateOrderUseCase(get()) }

    // ViewModel
    viewModel {
        RestaurantMenuViewModel(get())
    }
    viewModel {
        CartViewModel(get(), get(), get(), get())
    }
}


