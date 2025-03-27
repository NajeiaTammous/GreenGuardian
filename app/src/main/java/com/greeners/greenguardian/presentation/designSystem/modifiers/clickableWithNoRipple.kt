package com.greeners.greenguardian.presentation.designSystem.modifiers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

fun Modifier.clickableWithNoRipple(enabled: Boolean = true, onClick: () -> Unit) = then(
    clickable(
        enabled = enabled,
        interactionSource = MutableInteractionSource(),
        indication = null,
    ) {
        onClick()
    }
)
