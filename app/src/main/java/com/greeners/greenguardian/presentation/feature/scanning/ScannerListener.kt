package com.greeners.greenguardian.presentation.feature.scanning

import android.net.Uri

interface ScannerListener {
    fun updateCapturedPhoto(uri: Uri)
    fun onReceive(effect: ScannerUiEffect)
}