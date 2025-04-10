package com.greeners.greenguardian.data.remote.response

import com.google.gson.annotations.SerializedName
import com.greeners.greenguardian.domain.model.PlantSearch

data class PlantSearchDto (
    @SerializedName("entities")
    val entities: List<PlantDto>,
    @SerializedName("entities_trimmed")
    val entitiesTrimmed: Boolean,
    @SerializedName("limit")
    val limit: Int,
){
    fun toPlantSearchEntity(): PlantSearch {
        return PlantSearch(
            entities = entities.map { it.toPlantEntity() }
        )
    }
}