package com.greeners.greenguardian.presentation.feature.scanning.dialogs

import com.greeners.greenguardian.R

data class InstructionModel(
    val images: List<Int> = listOf(
        R.drawable.image_placeholder,
        R.drawable.image_placeholder,
    ),
    val instructions: String = "Make sure your plant is in foucas and well- lit. Avoid using LED grow lights.",

)
