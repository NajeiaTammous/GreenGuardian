package com.greeners.greenguardian.presentation.feature.plant.plants

import com.greeners.greenguardian.domain.model.Plant

data class PlantsUiState(
    val plants: List<Plant> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String = "",
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
)