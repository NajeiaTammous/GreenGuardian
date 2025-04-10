package com.greeners.greenguardian.presentation.feature.plant.plant_health

import com.greeners.greenguardian.domain.GreenGuardianRepository
import com.greeners.greenguardian.presentation.feature.base.BaseViewModel


class PlantHealthDetailsViewModel(
    private val repository: GreenGuardianRepository,
    private val plantId: String
) : BaseViewModel<PlantHealthDetailsUiState, PlantHealthDetailsUiEffect>(PlantHealthDetailsUiState()),
    PlantHealthDetailsInteractionListener {
    init {
        getPlantHealthDetails()
    }

    private fun getPlantHealthDetails() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(//todo: use plant id instead
            { repository.getPlantHealthDetails(plantId = plantId) },
            onSuccess = { plantHealthDetails ->
                updateState {
                    it.copy(
                        isLoading = false,
                        error = "",
                        diseaseName = plantHealthDetails.diseaseName,
                        description = plantHealthDetails.description,
                        chemicalTreatment = plantHealthDetails.chemicalTreatment,
                        biologicalTreatment = plantHealthDetails.biologicalTreatment,
                        preventionTreatment = plantHealthDetails.preventionTreatment,
                        similarDiseaseImages = plantHealthDetails.similarDiseaseImages.take(3),
                    )

                }
            },
            onError = {
                updateState { it.copy(isLoading = false, error = "Something went wrong!") }
            }
        )
    }

    override fun onBackClicked() {
        sendNewEffect(PlantHealthDetailsUiEffect.onBackClicked)
    }

}