package com.greeners.greenguardian.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.greeners.greenguardian.presentation.feature.home.HomeScreen
import com.greeners.greenguardian.presentation.feature.plant.DiseaseDetailsScreen
import com.greeners.greenguardian.presentation.feature.plant.PlantDetailsScreen
import com.greeners.greenguardian.presentation.feature.plant.PlantsScreen
import com.greeners.greenguardian.presentation.feature.scanning.ScannerScreen
import com.greeners.greenguardian.presentation.feature.scanning.ScanningPreviewScreen
import com.greeners.greenguardian.presentation.feature.search.SearchScreen


@Composable
fun Navigation(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.HomeScreen> {
            HomeScreen(
                onButtonClicked = { navController.navigate(Screen.SearchScreen) }
            )
        }
        composable<Screen.SearchScreen> {
            SearchScreen(
                onPlantSelected = { plantId ->
                    navController.navigate(Screen.PlantDetailsScreen(plantId))
                }
            )
        }
        composable<Screen.ScannerScreen> {
            ScannerScreen()
        }
        composable<Screen.ScanningPreviewScreen> {
            ScanningPreviewScreen()
        }
        composable<Screen.PlantsScreen> {
            PlantsScreen()
        }
        composable<Screen.PlantDetailsScreen> { backStackEntry ->
            val plantId = backStackEntry.toRoute<Screen.PlantDetailsScreen>().plantId
            PlantDetailsScreen(plantId = plantId)
        }
        composable<Screen.DiseaseDetailsScreen> {
            DiseaseDetailsScreen()
        }
    }
}
