package com.greeners.greenguardian.presentation.feature.plant.details

import com.greeners.greenguardian.domain.GreenGuardianRepository
import com.greeners.greenguardian.presentation.feature.base.BaseViewModel
import com.greeners.greenguardian.presentation.feature.home.HomeInteractionListener
import com.greeners.greenguardian.presentation.feature.home.HomeUiEffect
import com.greeners.greenguardian.presentation.feature.home.HomeUiState

class PlantDetailsViewModel(
    private val repository: GreenGuardianRepository,
    private val id: String
) : BaseViewModel<PlantDetailsUIState, PlantDetailsUiEffect>(PlantDetailsUIState()),
    PlantDetailsInteractionListener {
    init {
        getPlantDetails()
    }

    private fun getPlantDetails() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            { repository.getPlantDetails(id) },
            onSuccess = { plantDetails ->
                updateState {
                    it.copy(
                        isLoading = false,
                        error = null,
                        plantDetails = plantDetails.copy(images = plantDetails.images.take(3)),
                    )
                }
            },
            onError = {
                updateState { it.copy(isLoading = false, error = "Something went wrong!") }
            }
        )
    }

    override fun onClickBack() {
        sendNewEffect(PlantDetailsUiEffect.OnClickBack)
    }

}