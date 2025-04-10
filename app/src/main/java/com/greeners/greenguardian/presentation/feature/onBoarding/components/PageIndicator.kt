package com.greeners.greenguardian.presentation.feature.onBoarding.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = LocalColors.current.contentSecondary,
    inactiveColor: Color = LocalColors.current.contentSecondary.copy(alpha = 0.2f),
    dotSize: Dp = 8.dp,
    activeDotWidth: Dp = 24.dp,
    spacing: Dp = 8.dp,
    animationDuration: Int = 300
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val animatedWidth by animateDpAsState(
                targetValue = if (index == currentPage) activeDotWidth else dotSize,
                animationSpec = tween(durationMillis = animationDuration)
            )

            val animatedColor by animateColorAsState(
                targetValue = if (index == currentPage) activeColor else inactiveColor,
                animationSpec = tween(durationMillis = animationDuration)
            )

            Box(
                modifier = Modifier
                    .height(dotSize)
                    .width(animatedWidth)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(animatedColor)
            )
        }
    }
}