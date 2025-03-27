package com.greeners.greenguardian.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.greeners.greenguardian.presentation.feature.onBoarding.OnBoardingScreen


fun NavGraphBuilder.onBoardingScreenRoute(onNavigateBack: () -> Unit) {
    composable(
        route = Screen.Main.route
    ) {
        OnBoardingScreen(
            onNavigateTo = { },
            navigateBack = onNavigateBack
        )
    }
}