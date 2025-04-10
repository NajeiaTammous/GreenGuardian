package com.greeners.greenguardian.presentation.feature.plant.plant_health


data class PlantHealthDetailsUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val similarDiseaseImages: List<String>? = null,
    val diseaseName: String = "",
    val description: String = "",
    val chemicalTreatment: String = "",
    val biologicalTreatment: String = "",
    val preventionTreatment: String = "",
)