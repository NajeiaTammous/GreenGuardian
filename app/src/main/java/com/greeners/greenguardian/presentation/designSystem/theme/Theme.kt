package com.greeners.greenguardian.presentation.designSystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object Theme {
    val color: GreenGuardianColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val shape: GreenGuardianShape
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val typography: GreenGuardianTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTextStyle.current

}