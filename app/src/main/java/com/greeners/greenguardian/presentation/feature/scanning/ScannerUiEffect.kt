package com.greeners.greenguardian.presentation.feature.scanning

import android.content.Context
import android.net.Uri

sealed interface ScannerUiEffect {
    data object NavigateToReadMore : ScannerUiEffect
    data object OnErrorShown : ScannerUiEffect
    data class OnPermissionGrantedWith(val compositionContext: Context) : ScannerUiEffect
    data object OnPermissionDenied : ScannerUiEffect
    data class OnImageSavedWith(val compositionContext: Context) : ScannerUiEffect
    data object OnImageSavingCanceled : ScannerUiEffect
    data object OnCloseButtonClicked : ScannerUiEffect
    data class ProcessPlantImage(val context: Context, val imageUri: Uri) : ScannerUiEffect
}