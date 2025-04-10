package com.greeners.greenguardian.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.greeners.greenguardian.presentation.feature.home.HomeScreen
import com.greeners.greenguardian.presentation.feature.onBoarding.OnBoardingScreen
import com.greeners.greenguardian.presentation.feature.onBoarding.OnBoardingUiEffect
import com.greeners.greenguardian.presentation.feature.plant.details.PlantDetailsScreen
import com.greeners.greenguardian.presentation.feature.plant.plant_health.PlantHealthDetailsScreen
import com.greeners.greenguardian.presentation.feature.plant.plants.PlantsScreen
import com.greeners.greenguardian.presentation.feature.scanning.ScannerScreen
import com.greeners.greenguardian.presentation.feature.scanning.ScanningPreviewScreen
import com.greeners.greenguardian.presentation.feature.search.SearchScreen

@SuppressLint("ContextCastToActivity")
@Composable
fun Navigation(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.OnBoardingScreen
    ) {

        composable<Screen.OnBoardingScreen> {
            OnBoardingScreen(
                onNavigateTo = { effect ->
                    if (effect == OnBoardingUiEffect.NavigateToMain) {
                        navController.navigate(Screen.HomeScreen) {
                            popUpTo(Screen.OnBoardingScreen) { inclusive = true }
                        }
                    }
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable<Screen.HomeScreen> {
            HomeScreen(
                onSearchBarClicked = { navController.navigate(Screen.SearchScreen) },
                onImageSelected = { imageUri ->
                    navController.navigate(Screen.ScannerScreen(imageUri))
                },
                onCameraScanClicked = {
                    navController.navigate(Screen.ScannerScreen(null))
                },
                onPlantClicked = { plantId ->
                    navController.navigate(Screen.PlantDetailsScreen(plantId))
                },
                onSeeAllClicked = {
                    navController.navigate(Screen.PlantsScreen)
                }
            )
        }
        composable<Screen.SearchScreen> {
            SearchScreen(
                onPlantSelected = { plantId ->
                    navController.navigate(Screen.PlantDetailsScreen(plantId))
                }
            )
        }
        composable<Screen.ScannerScreen> { backStackEntry ->
            val imageUri = backStackEntry.toRoute<Screen.ScannerScreen>().imageUri

            ScannerScreen(
                imageUri,
                onBackPressed = {
                    navController.popBackStack()
                },
                onDiseaseReadMoreClick = { accessToken ->
                    navController.navigate(
                        Screen.PlantHealthDetailsScreen(plantId = accessToken),
                    )
                },
            )
        }
        composable<Screen.ScanningPreviewScreen> { backStackEntry ->
            val imageUri = backStackEntry.toRoute<Screen.ScanningPreviewScreen>().imageUri
            ScanningPreviewScreen(imageUri = imageUri)
        }
        composable<Screen.PlantsScreen> {
            PlantsScreen(
                onPlantSelected = { plantId ->
                    navController.navigate(Screen.PlantDetailsScreen(plantId))
                },
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable<Screen.PlantDetailsScreen> { backStackEntry ->
            val plantId = backStackEntry.toRoute<Screen.PlantDetailsScreen>().plantId
            PlantDetailsScreen(plantId = plantId, onBackClick = { navController.navigateUp() })
        }
        composable<Screen.PlantHealthDetailsScreen> { backStackEntry ->
            val plantId = backStackEntry.toRoute<Screen.PlantDetailsScreen>().plantId
            PlantHealthDetailsScreen(
                plantId = plantId,
                onBackClicked = { navController.navigateUp() })
        }

    }
}