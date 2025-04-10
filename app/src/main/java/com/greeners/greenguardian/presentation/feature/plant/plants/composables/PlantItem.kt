package com.greeners.greenguardian.presentation.feature.plant.plants.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClickItem(itemId) }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f),
                shape = LocalShapes.current.mediumRoundedCorners,
                colors = CardDefaults.cardColors(
                    containerColor = LocalColors.current.onPrimary,
                ),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.8f),
                    model = image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                name,
                modifier = Modifier
                    .padding(bottom = 8.dp),
                style = LocalTextStyle.current.titleSmall,
                color = LocalColors.current.contentPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
private fun PlantItemPreview() {
    PlantItem(
        modifier = Modifier.padding(8.dp),
        name = "Snake Plant",
        itemId = "1",
        onClickItem = {},
        image = "https://plant-id.ams3.cdn.digitaloceanspaces.com/knowledge_base/wikipedia/1c6/1c6e0d63a351532d3c391092a5f12dfb0fa236e5.jpg"
    )
}