package com.greeners.greenguardian.presentation.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.greeners.greenguardian.presentation.designSystem.GreenGuardianTheme
import com.greeners.greenguardian.presentation.designSystem.theme.darkThemeColors
import com.greeners.greenguardian.presentation.designSystem.theme.lightThemeColors
import com.greeners.greenguardian.presentation.navigation.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
       installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = lightThemeColors.background.toArgb(),
                darkScrim = darkThemeColors.background.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = lightThemeColors.background.toArgb(),
                darkScrim = darkThemeColors.background.toArgb()
            )
        )
        setContent {
            GreenGuardianTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
