package com.example.fooddeliveryapp.di

import com.example.fooddeliveryapp.data.repository.*
import com.example.fooddeliveryapp.data.source.local.CartDataSource
import com.example.fooddeliveryapp.data.source.local.OrderLocalStore
import com.example.fooddeliveryapp.data.source.remote.DummyRestaurantData
import com.example.fooddeliveryapp.data.source.remote.FoodMenuApi
import com.example.fooddeliveryapp.domain.useCase.*
import com.example.fooddeliveryapp.presentation.ui.viewModel.AppStartViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.CartViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.HomeViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.RestaurantMenuViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.fooddeliveryapp.presentation.ui.viewModel.OrderStatusViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidContext

val appModule = module {

    // DATA SOURCES
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    single {
        FoodMenuApi(get())
    }


    single { DummyRestaurantData }
    single { CartDataSource() }
    single { FirebaseFirestore.getInstance() }
    single { OrderLocalStore(androidContext()) }

    // REPOSITORIES
    single { FoodMenuInfoRepository(get()) }
    single { CartRepository(get()) }
    single { RestaurantRepositoryImpl() }
    single { CartRepository(get()) }
    single {
        OrderRepositoryImpl(
            firestore = get()
        )
    }

    // USE CASES
    factory { GetFoodMenuUseCase(get()) }
    factory { GetCartItemsUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { ClearCartUseCase(get()) }
    factory { GetRestaurantsUseCase(get()) }

    factory {
        CreateOrderUseCase(
            orderRepository = get(),
            orderLocalStore = get()
        )
    }

    // VIEW MODELS
    viewModel {
        HomeViewModel(
            getRestaurantUsecase = get()
        )
    }

    viewModel {
        RestaurantMenuViewModel(get())
    }

//    viewModel {
//        CartViewModel(
//            get(),
//            get(),
//            get(),
//            get(),
//            get()
//        )
//    }

    viewModel {
        OrderStatusViewModel(
            orderRepository = get(),
            orderLocalStore = get()
        )
    }

    viewModel {
        AppStartViewModel(
            get()
        )
    }
    single {
        CartViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

}
