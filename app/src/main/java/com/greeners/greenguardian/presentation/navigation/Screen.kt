package com.greeners.greenguardian.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Created by Aziza Helmy on 4/3/2025.
 */
sealed interface Screen {
    @Serializable
    data object HomeScreen:Screen
    @Serializable
    data object ScannerScreen:Screen
    @Serializable
    data object SearchScreen:Screen
    @Serializable
    data object ScanningPreviewScreen:Screen
    @Serializable
    data object PlantsScreen:Screen
    @Serializable
    data class PlantDetailsScreen(val plantId:String):Screen
    @Serializable
    data class DiseaseDetailsScreen(val plantId:String):Screen


}