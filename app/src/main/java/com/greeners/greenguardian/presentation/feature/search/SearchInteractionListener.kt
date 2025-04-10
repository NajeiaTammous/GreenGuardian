package com.greeners.greenguardian.presentation.feature.search

interface SearchInteractionListener {
    fun onSearchTextChange(query: String)
    fun onPlantSelected(plantId: String)
}