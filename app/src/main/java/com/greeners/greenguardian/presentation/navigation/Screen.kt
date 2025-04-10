package com.greeners.greenguardian.presentation.navigation

import kotlinx.serialization.Serializable


sealed interface Screen {
    @Serializable
    data object SplashScreen:Screen
    @Serializable
    data object OnBoardingScreen : Screen
    @Serializable
    data object HomeScreen:Screen
    @Serializable
    data class ScannerScreen(val imageUri: String?):Screen
    @Serializable
    data object SearchScreen:Screen
    @Serializable
    data class ScanningPreviewScreen(val imageUri: String):Screen
    @Serializable
    data object PlantsScreen:Screen
    @Serializable
    data class PlantDetailsScreen(val plantId:String):Screen
    @Serializable
    data class PlantHealthDetailsScreen(val plantId: String): Screen


}