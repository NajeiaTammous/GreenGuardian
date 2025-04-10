package com.greeners.greenguardian.di


import com.greeners.greenguardian.presentation.feature.home.HomeViewModel
import com.greeners.greenguardian.presentation.feature.onBoarding.OnBoardingViewModel
import com.greeners.greenguardian.presentation.feature.plant.details.PlantDetailsViewModel
import com.greeners.greenguardian.presentation.feature.plant.plants.PlantsViewModel
import com.greeners.greenguardian.presentation.feature.scanning.ScannerViewModel
import com.greeners.greenguardian.presentation.feature.plant.plant_health.PlantHealthDetailsViewModel
import com.greeners.greenguardian.presentation.feature.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::OnBoardingViewModel)
    viewModelOf(::PlantsViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::HomeViewModel)
    viewModel { (id: String) ->
        PlantDetailsViewModel(get(), id)
    }
    viewModel {
        ScannerViewModel(get())
    }
    viewModel { (id: String) ->
        PlantHealthDetailsViewModel(get(), id)
    }
}
