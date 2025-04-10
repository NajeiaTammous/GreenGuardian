package com.greeners.greenguardian.presentation.feature.plant.plants

interface PlantsUiEffect {
    data class OnClickPlantCard(val plantId: String) : PlantsUiEffect
    object OnBackPressed : PlantsUiEffect
}