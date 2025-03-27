package com.greeners.greenguardian.presentation.designSystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import com.greeners.greenguardian.presentation.designSystem.language.LocalizationManager
import com.greeners.greenguardian.presentation.designSystem.theme.GreenGuardianTypography
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.designSystem.theme.LocalShapes
import com.greeners.greenguardian.presentation.designSystem.theme.LocalTextStyle
import com.greeners.greenguardian.presentation.designSystem.theme.darkThemeColors
import com.greeners.greenguardian.presentation.designSystem.theme.defaultShape
import com.greeners.greenguardian.presentation.designSystem.theme.lightThemeColors

@Composable
fun GreenGuardianTheme(
    darkTheme: Boolean = false,
    languageCode: String = "en",
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) darkThemeColors else lightThemeColors

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTextStyle provides GreenGuardianTypography(),
        LocalLayoutDirection provides LocalizationManager.getLayoutDirection(languageCode.lowercase()),
        LocalShapes provides defaultShape,
    ) {
        content()
    }
}