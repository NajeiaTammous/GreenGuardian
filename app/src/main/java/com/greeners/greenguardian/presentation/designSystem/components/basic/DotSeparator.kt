package com.greeners.greenguardian.presentation.designSystem.components.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.presentation.designSystem.theme.Theme

@Composable
fun DotSeparator(
    modifier: Modifier = Modifier,
    color: Color = Theme.color.contentTertiary
) {
    Box(modifier = modifier.size(3.dp).clip(CircleShape).background(color))
}