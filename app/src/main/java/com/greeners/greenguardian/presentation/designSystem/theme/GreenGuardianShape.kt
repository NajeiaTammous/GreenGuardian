package com.greeners.greenguardian.presentation.designSystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp


data class GreenGuardianShape(
    val fullRounded: Shape,
    val smallRoundedCorners: Shape,
    val mediumRoundedCorners: Shape,
    val normalRoundedCorners: Shape,
)

internal val defaultShape = GreenGuardianShape(
    fullRounded = RoundedCornerShape(100),
    smallRoundedCorners = RoundedCornerShape(4.dp),
    mediumRoundedCorners = RoundedCornerShape(8.dp),
    normalRoundedCorners = RoundedCornerShape(16.dp)
)

internal val LocalShapes = staticCompositionLocalOf { defaultShape }