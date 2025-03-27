package com.greeners.greenguardian.presentation.designSystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class GreenGuardianColor(
    val primary: Color,
    val primaryTint: Color,
    val contentPrimary: Color,
    val contentSecondary: Color,
    val contentTertiary: Color,
    val divider: Color,
    val surface: Color,
    val onPrimary: Color,
    val background: Color,
    val disable: Color,
    val onDisable: Color,
    val error: Color,
    val errorContainer: Color,
    val success:Color,
    val successContainer:Color,
    val warning: Color,
    val warningContainer: Color,
)

internal val lightThemeColors = GreenGuardianColor(
    primary = Color(0xFF1DA055),
    primaryTint = Color(0xFFE5FAEE),
    contentPrimary = Color(0xFF031109),
    contentSecondary = Color(0xFF6F7673),
    contentTertiary = Color(0xFFB7BAB9),
    divider = Color(0xFFECEDEC),
    surface = Color(0xFFFAFAFA),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFFF3F3F3),
    disable = Color(0xFFD8D4D9),
    onDisable = Color(0xFF989898),
    error = Color(0xFF9C0000),
    errorContainer = Color(0xFFF6E6E6), //0A 04
    success = Color(0xFF41BE88),
    successContainer = Color(0xFFF0FFF7),
    warning = Color(0xFFF2BD00),
    warningContainer = Color(0xFFFFFCEB),
)


internal val darkThemeColors = GreenGuardianColor(
    primary = Color(0xFF1DA055),
    primaryTint = Color(0xFFE5FAEE),
    contentPrimary = Color(0xFF031109),
    contentSecondary = Color(0xFF6F7673),
    contentTertiary = Color(0xFFB7BAB9),
    divider = Color(0xFFECEDEC),
    surface = Color(0xFFFAFAFA),
    onPrimary = Color(0xFFFFFFFF),
    background = Color(0xFFF3F3F3),
    disable = Color(0xFFD8D4D9),
    onDisable = Color(0xFF989898),
    error = Color(0xFF9C0000),
    errorContainer = Color(0xFFF6E6E6), //0A 04
    success = Color(0xFF41BE88),
    successContainer = Color(0xFFF0FFF7),
    warning = Color(0xFFF2BD00),
    warningContainer = Color(0xFFFFFCEB),
)


internal val LocalColors = staticCompositionLocalOf { lightThemeColors }