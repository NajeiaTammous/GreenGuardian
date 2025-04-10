package com.greeners.greenguardian.presentation.feature.search

import com.greeners.greenguardian.domain.model.Plant

data class SearchUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val searchResults: List<Plant> = emptyList(),
    val isSearching: Boolean = false,
    val isEmpty: Boolean = false,
    val isInit: Boolean = true,

)