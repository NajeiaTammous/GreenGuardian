package com.greeners.greenguardian.presentation.feature.home

import android.net.Uri

interface HomeInteractionListener {
    fun onClickSearch()
    fun onClickToSelectImage(imageType: ImageType)
    fun onClickPlantCard(plantId: String)
    fun onImageSelected(photoUri: Uri)
    fun onClickScan()
    fun onScanDialogDismissed()
    fun onClickSeeAll()
    
}