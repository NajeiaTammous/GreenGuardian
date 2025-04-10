package com.greeners.greenguardian.domain.model

data class PlantDetails(
    val id: String,
    val name: String,
    val genus: String,
    val images: List<String>,
    val description: String,
    val seeds: String,
    val pesticide: String,
    val water: String,
)
