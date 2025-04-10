package com.greeners.greenguardian.presentation.feature.home

import com.greeners.greenguardian.domain.model.Plant

data class HomeUiState(
    val isLoading: Boolean = false,
    val isScanDialogVisible: Boolean = false,
    val error: String = "",
    val isRefreshing: Boolean = false,
    val plants: List<Plant> = emptyList(),
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
)