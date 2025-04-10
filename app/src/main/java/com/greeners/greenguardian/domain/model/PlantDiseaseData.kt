package com.greeners.greenguardian.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlantDiseaseData(
    val accessToken: String,
    val isHealthy: Boolean,
    val isPlant: Boolean,
    val id: String,
    val name: String,
    val description: String,
    val similarImages: List<String>,
    val treatment: Treatment,
) {
    @Serializable
    data class Treatment(
        val chemical: List<String> = emptyList(),
        val biological: List<String> = emptyList(),
        val prevention: List<String> = emptyList(),
    )
}

