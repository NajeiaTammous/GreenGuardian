package com.greeners.greenguardian.presentation.designSystem.modifiers

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            val rightPixel = size.width + offsetX.toPx()
            val bottomPixel = size.height + offsetY.toPx()

            canvas.drawRect(
                left = (offsetX).toPx(),
                top = (offsetY).toPx(),
                right = size.width + offsetX.toPx(),
                bottom = size.height + offsetY.toPx(),
                paint = paint,
            )
        }
    }
)

fun Modifier.circleShadow(
    color: Color = Color.Green, // مثلاً
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = this.then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = BlurMaskFilter(
                    blurRadius.toPx(),
                    BlurMaskFilter.Blur.NORMAL
                )
            }

            frameworkPaint.color = color.copy(alpha = 0.2f).toArgb()

            val radius = size.minDimension / 2
            canvas.drawCircle(
                center = Offset(offsetX.toPx() + radius, offsetY.toPx() + radius),
                radius = radius,
                paint = paint
            )
        }
    }
)

@Composable
fun CircleGradient(
    modifier: Modifier = Modifier,
    innerColor: Color,
    outerColor: Color
) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    innerColor.copy(alpha = 0.35f),
                    innerColor.copy(alpha = 0.25f),
                    outerColor.copy(alpha = 0.21f),
                    outerColor.copy(alpha = 0.011f)
                ),
                center = center,
                radius = radius
            )
        )
    }
}
