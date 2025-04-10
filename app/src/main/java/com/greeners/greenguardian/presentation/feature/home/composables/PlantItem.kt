package com.greeners.greenguardian.presentation.feature.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.designSystem.theme.LocalShapes
import com.greeners.greenguardian.presentation.designSystem.theme.LocalTextStyle

@Composable
fun PlantItem(
    modifier: Modifier = Modifier,
    name: String,
    itemId: String,
    onClickItem: (itemId: String) -> Unit,
    image: String,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .width(154.dp)
            .aspectRatio(0.8f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClickItem(itemId) }
            ), shape = LocalShapes.current.mediumRoundedCorners
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = image,
                contentDescription ="" ,
                contentScale = ContentScale.Crop
            )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        name,
                        style = LocalTextStyle.current.titleSmall,
                        color = LocalColors.current.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
        }
    }
}