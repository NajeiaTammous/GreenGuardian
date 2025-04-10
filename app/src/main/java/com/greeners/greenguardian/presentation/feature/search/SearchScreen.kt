package com.greeners.greenguardian.presentation.feature.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.R
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.feature.home.composables.CustomSearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    onPlantSelected: (plantId: String) -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val searchText by viewModel.searchQuery.collectAsState("")

   LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SearchUiEffect.OnClickPlantCard -> {
                    onPlantSelected(effect.plantId)
                }
            }
        }
   }

    SearchContent(
        searchText = searchText,
        state = state,
        listener = viewModel
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchContent(
    searchText: String,
    state: SearchUiState,
    listener: SearchInteractionListener
) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = LocalColors.current.background)
            .padding(vertical = 16.dp)) {
                CustomSearchBar(
                    hint = stringResource(R.string.search_hint),
                    onValueChange = listener::onSearchTextChange,
                    text = searchText,
                    iconPainter = painterResource(R.drawable.search_normal))
        if (state.isInit || searchText.isEmpty()) {
            SearchPlaceHolder(painterResource(R.drawable.init_search_state),
                stringResource(R.string.init_search))
        }else {

            if (state.searchResults.isEmpty()){
                SearchPlaceHolder(painterResource(R.drawable.empty_search),
                    stringResource(R.string.no_plant_found)
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(state.searchResults.size, key = { it }) { index ->
                    val plant = state.searchResults[index]
                    SearchItem(
                        name = plant.name,
                        onClickItem = { listener.onPlantSelected(plant.id) },
                        itemId = plant.id,
                        image = plant.imageResId,
                    )
                }
            }
        }
        }

}

@Composable
fun SearchPlaceHolder(
    image: Painter,
    text: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LocalColors.current.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Image(painter = image, contentDescription = "initial state",
            modifier = Modifier.padding(bottom = 24.dp))
        Text(text = text, color = LocalColors.current.contentSecondary)

    }
}