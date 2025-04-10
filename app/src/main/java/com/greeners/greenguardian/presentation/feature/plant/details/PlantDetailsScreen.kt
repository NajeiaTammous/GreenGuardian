package com.greeners.greenguardian.presentation.feature.plant.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greeners.greenguardian.R
import com.greeners.greenguardian.domain.model.PlantDetails
import com.greeners.greenguardian.presentation.designSystem.components.CarouselImage
import com.greeners.greenguardian.presentation.designSystem.components.BackButton
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.Theme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PlantDetailsScreen(
    plantId: String,
    onBackClick: () -> Unit,
    viewModel: PlantDetailsViewModel = koinViewModel { parametersOf(plantId) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is PlantDetailsUiEffect.OnClickBack -> onBackClick()
            }
        }
    }

    PlaintDetailsContent(state, viewModel)
}

@Composable
private fun PlaintDetailsContent(
    state: PlantDetailsUIState,
    interactionListener: PlantDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (state.isLoading.not()) {
            if (state.error.isNullOrBlank().not()) {
                Text(
                    text = state.error!!,
                    style = Theme.typography.bodyMedium,
                    color = Theme.color.error,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.color.background),
                ) {
                    imagesSection(state)
                    titleSection(state)
                    infoSection(state)
                }
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center),
                color = Theme.color.primary,
                strokeWidth = 4.dp
            )
        }
        BackButton(
            onClick = interactionListener::onClickBack,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
        )
    }
}

private fun LazyListScope.imagesSection(state: PlantDetailsUIState) {
    item {
        CarouselImage(state.plantDetails?.images ?: emptyList())
    }
}

private fun LazyListScope.titleSection(state: PlantDetailsUIState) {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = state.plantDetails?.name ?: "",
                style = Theme.typography.headlineMedium,
                color = Theme.color.contentPrimary,
            )
            Text(
                text = state.plantDetails?.genus ?: "",
                style = Theme.typography.titleSmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Thin
                ),
                color = Theme.color.contentSecondary,
            )
        }
    }
}

private fun LazyListScope.infoSection(state: PlantDetailsUIState) {
    item {
        var selectedIndex by remember { mutableIntStateOf(0) }
        Column(verticalArrangement = Arrangement.Top) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TabItem(
                    title = stringResource(R.string.about),
                    isSelected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 }
                )
                TabItem(
                    title = stringResource(R.string.placnt_care),
                    isSelected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 }
                )
            }

            AnimatedContent(selectedIndex, label = "Pager") {
                InfoContent(
                    page = it,
                    plantDetails = state.plantDetails,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun RowScope.TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = Theme.color.contentPrimary
    Row(
        modifier = Modifier
            .weight(1f)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            style = Theme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            ),
            color = if (isSelected) color else Theme.color.contentSecondary,
            modifier = modifier
                .drawBehind {
                    if (isSelected) {
                        drawLine(
                            color = color,
                            strokeWidth = 1.dp.toPx(),
                            start = Offset(0f, size.height + 4.dp.toPx()),
                            end = Offset(size.width, size.height + 4.dp.toPx())
                        )
                    }
                }
        )
    }
}

@Composable
fun InfoContent(
    page: Int,
    plantDetails: PlantDetails?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Theme.color.background),
        verticalArrangement = Arrangement.Top
    ) {
        when (page) {
            0 -> {
                Text(
                    text = plantDetails?.description ?: "",
                    style = Theme.typography.bodyMedium,
                    color = Theme.color.contentPrimary,
                    modifier = Modifier
                        .background(Theme.color.onPrimary, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                )
            }

            1 -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    InfoItem(
                        iconResId = R.drawable.ic_seeds,
                        title = stringResource(R.string.seeds),
                        value = plantDetails?.seeds ?: ""
                    )
                    InfoItem(
                        iconResId = R.drawable.ic_pesticide,
                        title = stringResource(R.string.pesticide),
                        value = plantDetails?.pesticide ?: ""
                    )
                    InfoItem(
                        iconResId = R.drawable.ic_water,
                        title = stringResource(R.string.water),
                        value = plantDetails?.water ?: ""
                    )
                }
            }
        }
    }
}

@Composable
fun InfoItem(
    iconResId: Int,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    if (value.isBlank()) return
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.onPrimary, shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconResId),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(start = 8.dp)
        ) {

            Text(
                text = title,
                style = Theme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Thin
                ),
                color = Theme.color.contentTertiary,
            )
            Text(
                text = value,
                style = Theme.typography.bodyMedium,
                color = Theme.color.contentPrimary,
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PlantDetailsPreview() {
    val state = PlantDetailsUIState(
        isLoading = false,
        error = null,
        plantDetails = PlantDetails(
            id = "1",
            name = "Rose",
            genus = "Rosa",
            description = "A beautiful flower",
            images = listOf(
                "https://www.portalrosas.com/Imagenes/corazon-de-rosas.jpg",
                "https://www.portalrosas.com/Imagenes/corazon-de-rosas.jpg",
                ""
            ),
            water = "Moderate",
            seeds = "Seeds",
            pesticide = "None",
        )
    )

    PlaintDetailsContent(state = state, object : PlantDetailsInteractionListener {
        override fun onClickBack() {
            // Handle back click
        }
    })
}