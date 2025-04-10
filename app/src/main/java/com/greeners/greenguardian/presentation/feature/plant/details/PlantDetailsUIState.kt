package com.greeners.greenguardian.presentation.feature.plant.details

import com.greeners.greenguardian.domain.model.PlantDetails

data class PlantDetailsUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val plantDetails: PlantDetails? = null
)
