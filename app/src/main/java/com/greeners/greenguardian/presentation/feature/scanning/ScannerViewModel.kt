package com.greeners.greenguardian.presentation.feature.scanning

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.greeners.greenguardian.domain.GreenGuardianRepository
import com.greeners.greenguardian.domain.model.PlantDiseaseData
import com.greeners.greenguardian.presentation.feature.base.BaseViewModel
import com.greeners.greenguardian.presentation.utils.toMultipart
import kotlinx.coroutines.delay
import java.io.File

class ScannerViewModel(
    private val repository: GreenGuardianRepository
) : BaseViewModel<ScannerUiState, ScannerUiEffect>(ScannerUiState()),
    ScannerListener {

    override fun updateCapturedPhoto(image: Uri) {
        updateState {
            it.copy(imageUri = image)
        }
    }

    fun updateImageUri(imageUri: Uri) {
        updateState {
            it.copy(imageUri = imageUri)
        }
    }

    override fun onReceive(effect: ScannerUiEffect) {
        when (effect) {
            is ScannerUiEffect.OnPermissionGrantedWith -> {

                val photoFile = File.createTempFile(
                    "temp_plant_image",
                    ".jpg",
                    effect.compositionContext.cacheDir
                )

                val photoUri = FileProvider.getUriForFile(
                    effect.compositionContext,
                    "${effect.compositionContext.packageName}.provider",
                    photoFile
                )
                updateState {
                    it.copy(tempCacheFileUrl = photoUri)
                }
            }

            is ScannerUiEffect.NavigateToReadMore -> {}
            is ScannerUiEffect.OnImageSavedWith -> {

                val currentUri = state.value.tempCacheFileUrl
                if (currentUri != null) {
                    updateCapturedPhoto(currentUri)
                }
                // Reset temp cache URL
                updateState {
                    it.copy(tempCacheFileUrl = null)
                }
            }

            ScannerUiEffect.OnImageSavingCanceled -> {
                updateState {
                    it.copy(tempCacheFileUrl = null)
                }
            }

            ScannerUiEffect.OnPermissionDenied -> {
                updateState {
                    it.copy(error = "Camera permission denied")
                }
            }

            is ScannerUiEffect.ProcessPlantImage -> {
                processPlantImage(effect.imageUri, effect.context)
            }

            ScannerUiEffect.OnCloseButtonClicked -> {
                updateState {
                    it.copy(
                        isLoading = false,
                        error = null,
                        tempCacheFileUrl = null,
                        disease = null,
                        imageUri = null
                    )
                }
            }

            ScannerUiEffect.OnErrorShown -> {
                updateState {
                    it.copy(error = null)
                }
            }
        }
    }

    private fun processPlantImage(imageUri: Uri, context: Context) {
        updateState {
            it.copy(isLoading = true)
        }
        tryToExecute(
            function = {
                repository.detectPlantDiseases(
                    images = listOfNotNull(imageUri.toMultipart(context, partName = "images")),
                    latitude = 31.5017,  // Gaza latitude
                    longitude = 34.4668, // Gaza longitude
                )
            },
            onErrorWithThrowable = ::onDetectDiseasesError,
            onSuccess = ::onDetectDiseasesSuccess
        )
    }

    private fun onDetectDiseasesSuccess(plantDiseaseData: PlantDiseaseData) {
        getPlantIdentification(plantDiseaseData.accessToken)
    }

    private fun getPlantIdentification(token: String) {
        tryToExecute(
            function = {
                delay(500)
                repository.getPlantDiseaseIdentification(token)
            },
            onErrorWithThrowable = ::onGetPlantDiseasesError,
            onSuccess = ::onGetPlantDiseasesSuccess
        )
    }

    private fun onGetPlantDiseasesSuccess(plantDiseaseData: PlantDiseaseData) {
        with(plantDiseaseData) {
            val error: String? = when {
                !isPlant -> "no plant was detected"
                else -> null
            }
            updateState {
                it.copy(
                    disease = this,
                    error = error,
                    isPlantHealthy = null,
                    isLoading = false,
                )
            }
        }
    }

    private fun onGetPlantDiseasesError(throwable: Throwable) {
        updateErrorState(throwable)
    }

    private fun onDetectDiseasesError(throwable: Throwable) {
        updateErrorState(throwable)
    }

    private fun updateErrorState(throwable: Throwable) {
        updateState {
            it.copy(
                error = throwable.message ?: "Failed to detect plant diseases",
                isLoading = false
            )
        }
    }
    fun clearData(){
        updateState {
            it.copy(
                disease = null,
                error = null,
                isPlantHealthy = null,
                isLoading = false,
                imageUri = null
            )
        }
    }
}