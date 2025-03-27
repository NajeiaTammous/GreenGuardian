package com.greeners.greenguardian.presentation.designSystem.components.basic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.presentation.designSystem.theme.Theme

@Composable
fun HorizontalDivider(modifier: Modifier = Modifier){
    HorizontalDivider(
        modifier = modifier.fillMaxWidth(),
        thickness = (0.5).dp,
        color = Theme.color.divider
    )
}

@Composable
fun VerticalDivider(modifier: Modifier = Modifier){
    Box(
        modifier
            .width(1.dp)
            .background(color = Theme.color.divider)
    )
}


@Composable
fun DashedHorizontalDivider(
    thickness: Dp,
    color: Color = Theme.color.divider,
    phase: Float = 10f,
    intervals: FloatArray = floatArrayOf(20f, 20f),
    modifier: Modifier
) {
    Canvas(modifier = modifier) {
        val dividerHeight = thickness.toPx()
        drawRoundRect(
            color = color,
            style = Stroke(
                width = dividerHeight,
                pathEffect = PathEffect.dashPathEffect(intervals = intervals, phase = phase)
            )
        )
    }
}