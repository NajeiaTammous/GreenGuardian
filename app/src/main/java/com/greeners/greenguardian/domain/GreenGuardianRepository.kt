package com.greeners.greenguardian.domain

import com.greeners.greenguardian.domain.model.Plant
import com.greeners.greenguardian.domain.model.PlantDetails
import com.greeners.greenguardian.domain.model.PlantDiseaseData
import okhttp3.MultipartBody
import com.greeners.greenguardian.domain.model.PlantHealthDetails
import com.greeners.greenguardian.domain.model.PlantSearch

interface GreenGuardianRepository {
    suspend fun getCommonPlantsInPalestine(): List<String>
    suspend fun findPlantByName(plantName: String, limit: Int): PlantSearch
    suspend fun detectPlantDiseases(
        images: List<MultipartBody.Part>,
        latitude: Double,
        longitude: Double,
    ): PlantDiseaseData

    suspend fun getPlantDiseaseIdentification(token: String): PlantDiseaseData
    suspend fun getPlantDiseases(plantId: String): List<String>
    suspend fun getPlantDetails(plantId: String): PlantDetails
    suspend fun getPlantHealthDetails(plantId: String): PlantHealthDetails

    suspend fun getPlants(limit: Int? = null, query: String? = null): List<Plant>
}


