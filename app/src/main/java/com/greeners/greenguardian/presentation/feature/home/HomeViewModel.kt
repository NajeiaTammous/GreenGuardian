package com.greeners.greenguardian.presentation.feature.home

import android.net.Uri
import com.greeners.greenguardian.domain.GreenGuardianRepository
import com.greeners.greenguardian.presentation.feature.base.BaseViewModel

class HomeViewModel (
    private val repository: GreenGuardianRepository
) : BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()),
    HomeInteractionListener {
    init {
        getPlants()
    }

    private fun getPlants() {
        tryToExecute(
            function = {
                repository.getPlants(limit = 4)
            },
            onError = {},
            onSuccess = { plants ->
                updateState {
                    it.copy(
                        isLoading = false,
                        error = "",
                        plants = plants,
                        isEmpty = plants.isEmpty(),
                        isError = false
                    )
                }
            }
        )
    }

    override fun onClickScan() {
        updateState { it.copy(isScanDialogVisible =  !state.value.isScanDialogVisible ) }
    }

    override fun onScanDialogDismissed() {
        updateState { it.copy(isScanDialogVisible = false) }
    }
    override fun onClickSearch() {
        sendNewEffect(HomeUiEffect.OnClickSearch)
    }

    override fun onClickToSelectImage(imageType: ImageType) {
        sendNewEffect(HomeUiEffect.OnSelectedImageResource(imageType))
        updateState { it.copy(isScanDialogVisible = false) }
    }

    override fun onClickPlantCard(plantId: String) {
        sendNewEffect(HomeUiEffect.OnClickPlantCard(plantId))
    }

    override fun onImageSelected(photoUri: Uri) {
        sendNewEffect(HomeUiEffect.OnSelectedImage(photoUri))
    }

    override fun onClickSeeAll() {
        sendNewEffect(HomeUiEffect.OnClickSeeAll)
    }
}