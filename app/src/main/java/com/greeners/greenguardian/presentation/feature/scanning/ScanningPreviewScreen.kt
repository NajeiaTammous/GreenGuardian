package com.greeners.greenguardian.presentation.feature.scanning

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text


@Composable
fun ScanningPreviewScreen(
    imageUri: String,
    ) {

    Box(modifier = Modifier.fillMaxSize()){
        Text(text =imageUri)
    }
}