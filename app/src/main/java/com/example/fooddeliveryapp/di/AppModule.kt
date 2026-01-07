package com.example.fooddeliveryapp.di

import com.example.fooddeliveryapp.data.source.remote.FoodMenuApi
import com.example.fooddeliveryapp.data.repository.FoodMenuInfoRepository
import com.example.fooddeliveryapp.domain.repository.FoodMenuRepository
import com.example.fooddeliveryapp.domain.useCase.GetFoodMenuUseCase
import com.example.fooddeliveryapp.presentation.ui.viewModel.RestaurantMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // API
    single { FoodMenuApi() }

    // Repository
    single<FoodMenuRepository> {
        FoodMenuInfoRepository(get())
    }

    // Use case
    factory {
        GetFoodMenuUseCase(get())
    }

    // ViewModel
    viewModel {
        RestaurantMenuViewModel(get())
    }
}
