package com.example.fooddeliveryapp.di

import com.example.fooddeliveryapp.data.repository.RestaurantRepositoryImpl
import com.example.fooddeliveryapp.domain.repository.RestaurantRepository
import com.example.fooddeliveryapp.domain.usecase.GetRestaurantsUseCase
import com.example.fooddeliveryapp.presentaion.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
val appModule = module {

    // Data layer
    single<RestaurantRepository> {
        RestaurantRepositoryImpl()
    }

    // Domain layer
    factory {
        GetRestaurantsUseCase(get())
    }

    // ViewModel
    viewModel {
        HomeViewModel(get())
    }
}
