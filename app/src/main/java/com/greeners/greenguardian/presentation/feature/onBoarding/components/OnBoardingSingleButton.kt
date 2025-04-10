package com.greeners.greenguardian.presentation.feature.onBoarding.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.designSystem.theme.LocalShapes
import com.greeners.greenguardian.presentation.designSystem.theme.LocalTextStyle

@Composable
fun OnBoardingSingleButton(

    currentPage: Int,
    totalPages: Int,
    onNextOrSkip: () -> Unit
) {
    val colors = LocalColors.current
    val shapes = LocalShapes.current

    val isLastPage = currentPage == (totalPages - 1)
    val buttonText = if (isLastPage) "Skip" else "Next"

    Button(
        onClick = onNextOrSkip,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 70.dp),
        shape = shapes.smallRoundedCorners,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.primary,
            contentColor = colors.onPrimary
        )
    ) {
        Text(text = buttonText, style = LocalTextStyle.current.bodyLarge, color = colors.onPrimary)
    }
}