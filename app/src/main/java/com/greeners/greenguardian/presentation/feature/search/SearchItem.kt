package com.greeners.greenguardian.presentation.feature.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.designSystem.theme.LocalShapes
import com.greeners.greenguardian.presentation.designSystem.theme.LocalTextStyle
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    name: String,
    itemId: String,
    onClickItem: (itemId: String) -> Unit,
    image: String,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val decodedBytes: ByteArray = Base64.decode(image, Base64.DEFAULT)
    val bitmap: Bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    val imageBitmap: ImageBitmap = bitmap.asImageBitmap()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth().aspectRatio(0.8f)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { onClickItem(itemId) }
                ),
            border = BorderStroke(1.dp, color = LocalColors.current.divider),
            shape = LocalShapes.current.mediumRoundedCorners,
            colors = CardDefaults.cardColors(containerColor = LocalColors.current.surface),
        ) {

            Image(
                bitmap = imageBitmap,
                contentDescription = "Base64 Image",
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
        }
        Text(
            modifier = Modifier.padding(8.dp),
            text = name,
            style = LocalTextStyle.current.titleSmall,
            color = LocalColors.current.contentPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}