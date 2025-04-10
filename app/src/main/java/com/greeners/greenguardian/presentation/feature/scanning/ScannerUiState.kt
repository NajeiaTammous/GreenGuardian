package com.greeners.greenguardian.presentation.feature.scanning

import android.net.Uri
import com.greeners.greenguardian.domain.model.PlantDiseaseData

data class ScannerUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPlantHealthy: Boolean? = null,
    val isPlant: Boolean? = null,
    val imageUri: Uri? = null,
    val disease: PlantDiseaseData? = null,
    val tempCacheFileUrl: Uri? = null,
)