package com.greeners.greenguardian.presentation.feature.home

import android.net.Uri

interface HomeUiEffect {
    object OnClickSearch : HomeUiEffect
    object OnClickSeeAll : HomeUiEffect
    data class OnSelectedImageResource(val imageType: ImageType) : HomeUiEffect

    data class OnClickPlantCard(val plantId: String) : HomeUiEffect
    data class OnSelectedImage(val imageUri: Uri?) : HomeUiEffect
}
enum class ImageType {
    CAMERA,
    GALLERY,
}