package com.greeners.greenguardian.presentation.feature.plant.plants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greeners.greenguardian.domain.model.Plant
import com.greeners.greenguardian.presentation.designSystem.components.BackButton
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.designSystem.theme.Theme
import com.greeners.greenguardian.presentation.extension.Listen
import com.greeners.greenguardian.presentation.feature.plant.plants.composables.PlantItem
import org.koin.androidx.compose.koinViewModel


@Composable
fun PlantsScreen(
    viewModel: PlantsViewModel = koinViewModel(),
    onPlantSelected: (String) -> Unit = { },
    onBackClicked: () -> Unit = { }
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.effect.collectAsStateWithLifecycle(initialValue = null)

    effect.Listen {
        when (it) {
            is PlantsUiEffect.OnClickPlantCard ->
                onPlantSelected(it.plantId)

            is PlantsUiEffect.OnBackPressed ->
                onBackClicked()

            else -> {}
        }
    }

    PlantsContent(
        state = state,
        onPlantSelected = viewModel::onClickPlantCard,
        onBackClicked = viewModel::onBackPressed,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantsContent(
    state: PlantsUiState,
    onPlantSelected: (String) -> Unit,
    onBackClicked: () -> Unit,
) {
    //Temp Scaffold that should be replaced by app's scaffold containing the App's topAppBar.
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.background),
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                BackButton(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                        .align(Alignment.CenterStart)  ,
                    onClick = onBackClicked,
                )
//                Box(
//                    modifier = Modifier
//                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
//                        .border(
//                            width = 1.dp,
//                            color = Theme.color.divider,
//                            shape = RoundedCornerShape(8.dp)
//                        )
//                        .align(Alignment.CenterStart)
//                        .clip(shape = RoundedCornerShape(8.dp))
//                        .clickable {
//                            onBackClicked()
//                        },
//                    contentAlignment = Alignment.Center,
//                ) {
//                    Image(
//                        modifier = Modifier.padding(8.dp),
//                        imageVector = Icons.AutoMirrored.Filled.ArrowBack ,
//                        contentDescription = null,
//                    )
//                }
                Text(
                    "Palestinian Plants",
                    style = Theme.typography.headlineMedium,
                    color = Theme.color.contentPrimary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .background(LocalColors.current.background)
                .padding(
                    paddingValues = PaddingValues(
                        start = 8.dp,
                        top = padding.calculateTopPadding(),
                        end = 8.dp,
                        bottom = padding.calculateBottomPadding()
                    )
                )
        ) {
            when {
                state.isLoading -> FullScreenLoading()
                state.isError -> ErrorState(onRetry = { /* TODO: Retry logic */ })
                state.isEmpty -> EmptyState()
                else -> PlantList(
                    plants = state.plants,
                    onPlantSelected = onPlantSelected
                )
            }
        }
    }
}

@Composable
private fun PlantList(plants: List<Plant>, onPlantSelected: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        items(plants, key = { it.id }) { plant ->
            PlantItem(
                modifier = Modifier
                    .padding(8.dp),
                name = plant.name,
                itemId = plant.id,
                image = plant.imageResId,
                onClickItem = onPlantSelected
            )
        }
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Error loading plants")
        Button(onClick = onRetry, modifier = Modifier.padding(top = 16.dp)) {
            Text("Retry")
        }
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No plants found")
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFF070101)
@Composable
private fun PlantsScreenPreview() {
    PlantsContent(
        state = PlantsUiState(
            isLoading = false,
            isEmpty = false,
            isError = false,
            plants = listOf(
                Plant(
                    "1",
                    "Snake Plant",
                    "https://plant-id.ams3.cdn.digitaloceanspaces.com/knowledge_base/wikipedia/1c6/1c6e0d63a351532d3c391092a5f12dfb0fa236e5.jpg"
                ),
                Plant(
                    "2",
                    "Aloe Vera",
                    "https://plant-id.ams3.cdn.digitaloceanspaces.com/knowledge_base/wikidata/462/46200b659607cd7d6cf766db0a954391ac8d775d.jpg"
                ),
                Plant(
                    "3",
                    "Aloe Vera",
                    "https://plant-id.ams3.cdn.digitaloceanspaces.com/knowledge_base/wikidata/462/46200b659607cd7d6cf766db0a954391ac8d775d.jpg"
                ),
                Plant(
                    "4",
                    "Aloe Vera",
                    "https://plant-id.ams3.cdn.digitaloceanspaces.com/knowledge_base/wikidata/462/46200b659607cd7d6cf766db0a954391ac8d775d.jpg"
                ),
                Plant(
                    "5",
                    "Aloe Vera",
                    "https://plant-id.ams3.cdn.digitaloceanspaces.com/knowledge_base/wikidata/462/46200b659607cd7d6cf766db0a954391ac8d775d.jpg"
                ),
                Plant(
                    "6",
                    "Aloe Vera",
                    "https://plant-id.ams3.cdn.digitaloceanspaces.com/knowledge_base/wikidata/462/46200b659607cd7d6cf766db0a954391ac8d775d.jpg"
                )
            ),
            error = "",
        ),
        onPlantSelected = {},
        onBackClicked = {}
    )
}
