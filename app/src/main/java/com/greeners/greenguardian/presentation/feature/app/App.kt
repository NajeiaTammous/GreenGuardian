package com.greeners.greenguardian.presentation.feature.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.greeners.greenguardian.presentation.designSystem.GreenGuardianTheme
import com.greeners.greenguardian.presentation.navigation.Screen
import com.greeners.greenguardian.presentation.navigation.graph.RootNavGraph

@Composable
fun App() {
    GreenGuardianTheme {
            val navController = rememberNavController()
            RootNavGraph(navController = navController, startDestination = Screen.Main)
    }
}
