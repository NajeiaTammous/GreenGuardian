package com.greeners.greenguardian.presentation.feature.search

sealed interface SearchUiEffect{
    data class OnClickPlantCard(val plantId: String): SearchUiEffect
}