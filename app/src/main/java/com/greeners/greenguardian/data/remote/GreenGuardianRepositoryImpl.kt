package com.greeners.greenguardian.data.remote

import android.util.Log
import com.greeners.greenguardian.data.BaseRepository
import com.greeners.greenguardian.data.remote.service.GreenGuardianApiService
import com.greeners.greenguardian.data.remote.static_data.fakePlants
import com.greeners.greenguardian.domain.GreenGuardianRepository
import com.greeners.greenguardian.domain.model.Plant
import com.greeners.greenguardian.domain.model.PlantDetails
import com.greeners.greenguardian.domain.model.PlantHealthDetails
import com.greeners.greenguardian.domain.model.PlantDiseaseData
import com.greeners.greenguardian.domain.model.PlantSearch
import okhttp3.MultipartBody

class GreenGuardianRepositoryImpl(
    private val greenGuardianService: GreenGuardianApiService
) : BaseRepository(), GreenGuardianRepository {
    override suspend fun getCommonPlantsInPalestine(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun findPlantByName(plantName: String, limit: Int): PlantSearch {
        return wrap {
            greenGuardianService.findPlantByName(
                plantName = plantName,
                limit = limit
            )
        }.entities.mapNotNull { it.toPlantEntity() }
            .let { PlantSearch(it) }
    }

    override suspend fun detectPlantDiseases(
        images: List<MultipartBody.Part>,
        latitude: Double,
        longitude: Double
    ): PlantDiseaseData {
        return wrap {
            greenGuardianService.detectPlantDisease(
                images = images,
                latitude = latitude,
                longitude = longitude
            )
        }.toEntity()
    }

    override suspend fun getPlantDiseaseIdentification(token: String): PlantDiseaseData = wrap {
        Log.d("token", token)
        greenGuardianService.getPlantIdentification(accessToken = token)
    }.toEntity()

    override suspend fun getPlantDetails(plantId: String): PlantDetails {
        return wrap {
            greenGuardianService.getPlantDetails(
                accessToken = plantId,
            )
        }.toEntity()
    }

    override suspend fun getPlantHealthDetails(plantId: String): PlantHealthDetails {
        return wrap {
            greenGuardianService.getPlantHealthDetails(
                accessToken = plantId,
            )
        }.result.toEntity()
    }

    override suspend fun getPlants(limit: Int?, query: String?): List<Plant> {
        return fakePlants.filter { it.name.contains(query ?: "", ignoreCase = true) }
            .take(limit ?: fakePlants.size)
    }

    override suspend fun getPlantDiseases(plantId: String): List<String> {
        val response = wrap {
            greenGuardianService.getPlantHealthDetails(
                accessToken = plantId
            )
        }

        return response.result.disease.suggestions.map { it.name }
    }
}