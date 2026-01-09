package com.example.fooddeliveryapp.di

import com.example.fooddeliveryapp.data.repository.*
import com.example.fooddeliveryapp.data.source.local.CartDataSource
import com.example.fooddeliveryapp.data.source.remote.FoodMenuApi
import com.example.fooddeliveryapp.domain.useCase.*
import com.example.fooddeliveryapp.presentation.ui.viewModel.CartViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.HomeViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.RestaurantMenuViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    /* -------------------- API / DATA SOURCES -------------------- */

    single { FoodMenuApi() }
    single { CartDataSource() }
    single { FirebaseFirestore.getInstance() }

    /* -------------------- REPOSITORIES -------------------- */

    // Restaurants (Home)
    single<Repository> {
        RestaurantRepositoryImpl()
    }

    // Food menu
    single { FoodMenuInfoRepository(get()) }
    // Cart
    single {
        CartRepository(get())
    }

    // Orders
    single {
        OrderRepositoryImpl(get())
    }

    /* -------------------- USE CASES -------------------- */

    // Home
    factory {
        GetRestaurantsUseCase(get())
    }

    // Menu
    factory {
        GetFoodMenuUseCase(get())
    }

    // Cart
    factory { GetCartItemsUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { ClearCartUseCase(get()) }
    factory { CreateOrderUseCase(get()) }

    /* -------------------- VIEW MODELS -------------------- */


        viewModel {
            HomeViewModel(
                getRestaurantUsecase = get()
            )
        }



    viewModel {
        RestaurantMenuViewModel(get())
    }

    viewModel {
        CartViewModel(
            getCartItemsUseCase = get(),
            addToCartUseCase = get(),
            clearCartUseCase = get(),
            createOrderUseCase = get()
        )
    }
}
