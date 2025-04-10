package com.greeners.greenguardian.presentation.feature.onBoarding.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.presentation.designSystem.modifiers.CircleGradient
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors

@Composable
fun OnBoardingBackGround(
    modifier: Modifier = Modifier,
) {
    val colors = LocalColors.current

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(210.dp)
                .align(Alignment.TopStart)
                .offset(x = (-56).dp, y = (-16).dp)
        ) {
            CircleGradient(
                modifier = Modifier.fillMaxSize(),
                innerColor = colors.primary,
                outerColor = colors.primary
            )
        }

        Box(
            modifier = Modifier
                .size(210.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (56).dp, y = 24.dp)
        ) {
            CircleGradient(
                modifier = Modifier.fillMaxSize(),
                innerColor = colors.lemon.copy(alpha = 0.00001f),
                outerColor = colors.warning.copy(alpha = 0.3f)
            )
        }

        Box(
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-56).dp, y = 16.dp)
        ) {
            CircleGradient(
                modifier = Modifier.fillMaxSize(),
                innerColor = colors.primary.copy(alpha = 0.5f),
                outerColor = colors.primary.copy(alpha = 0.3f)
            )
        }
    }
}