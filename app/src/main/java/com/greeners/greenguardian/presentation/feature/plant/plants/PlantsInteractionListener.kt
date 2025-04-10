package com.greeners.greenguardian.presentation.feature.plant.plants

interface PlantsInteractionListener {
    fun onClickPlantCard(plantId: String)
    fun onBackPressed()
}