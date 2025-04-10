package com.greeners.greenguardian.presentation.designSystem.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.greeners.greenguardian.presentation.designSystem.theme.Theme

@Composable
fun CarouselImage(images: List<String>, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
    ) {
        val pagerState = rememberPagerState { images.size }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = images[it],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.onPrimary),
                contentScale = ContentScale.Crop,
            )
        }

        if (images.size > 1) {
            val currentPage = pagerState.currentPage
            val pageCount = pagerState.pageCount

            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomCenter),
            ) {
                repeat(pageCount) { page ->
                    val isSelected = currentPage == page
                    val width by animateDpAsState(
                        targetValue = if (isSelected) 20.dp else 6.dp,
                        label = "Indicator Animation"
                    )
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(width, 5.dp)
                            .clip(RoundedCornerShape(100))
                            .background(Color(0xFFD9D9D9))
                    )
                }
            }
        }

    }
}
