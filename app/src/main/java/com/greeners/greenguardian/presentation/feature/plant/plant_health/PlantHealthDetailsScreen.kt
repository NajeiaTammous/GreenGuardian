package com.greeners.greenguardian.presentation.feature.plant.plant_health

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greeners.greenguardian.presentation.designSystem.components.BackButton
import com.greeners.greenguardian.presentation.designSystem.components.CarouselImage
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.Theme
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun PlantHealthDetailsScreen(
    plantId: String,
    onBackClicked: () -> Unit,
    viewModel: PlantHealthDetailsViewModel = koinViewModel { parametersOf(plantId) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is PlantHealthDetailsUiEffect.onBackClicked -> onBackClicked()
            }
        }
    }
    PlantHealthDetailsContent(state, viewModel)
}

@Composable
fun PlantHealthDetailsContent(
    state: PlantHealthDetailsUiState,
    interactionListener: PlantHealthDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (state.isLoading.not()) {
            if (state.error.isBlank().not()) {
                Text(
                    text = state.error,
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
            onClick = interactionListener::onBackClicked,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
        )
    }
}

private fun LazyListScope.imagesSection(state: PlantHealthDetailsUiState) {
    item {
        CarouselImage(state.similarDiseaseImages ?: emptyList())
    }
}

private fun LazyListScope.infoSection(state: PlantHealthDetailsUiState) {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = state.diseaseName,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = state.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text("Treatments", style = MaterialTheme.typography.titleMedium)
                Text(
                    modifier = Modifier, text = "Chemical",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                TreatmentBullet(state.chemicalTreatment)

                Text(
                    "Biological",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                TreatmentBullet(state.biologicalTreatment)

                Text("Prevention", style = MaterialTheme.typography.titleMedium)
                TreatmentBullet(state.preventionTreatment)

            }
        }
    }
}


@Composable
fun TreatmentBullet(text: String) {
    Row(modifier = Modifier.padding(top = 8.dp)) {
        Text("•", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}