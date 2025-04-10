package com.greeners.greenguardian.presentation.feature.plant.plants

import com.greeners.greenguardian.domain.GreenGuardianRepository
import com.greeners.greenguardian.presentation.feature.base.BaseViewModel

class PlantsViewModel(
    private val repository: GreenGuardianRepository
) : BaseViewModel<PlantsUiState, PlantsUiEffect>(PlantsUiState()), PlantsInteractionListener {

    init {
        getPlants()
    }

    private fun getPlants() {
        tryToExecute(
            function = {
                repository.getPlants()
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

    override fun onClickPlantCard(plantId: String) {
        sendNewEffect(PlantsUiEffect.OnClickPlantCard(plantId))
    }

    override fun onBackPressed() {
        sendNewEffect(PlantsUiEffect.OnBackPressed)
    }

}