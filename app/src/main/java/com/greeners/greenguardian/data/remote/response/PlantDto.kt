package com.greeners.greenguardian.data.remote.response

import com.google.gson.annotations.SerializedName
import com.greeners.greenguardian.domain.model.Plant

data class PlantDto(
    @SerializedName("matched_in")
    val matchedIn: String,
    @SerializedName("matched_in_type")
    val matchedInType: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("match_position")
    val matchPosition: Int,
    @SerializedName("match_length")
    val matchLength: Int,
    @SerializedName("entity_name")
    val entityName: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
){
    fun toPlantEntity(): Plant {
        return Plant(
            id = accessToken,
            name = matchedIn,
            imageResId = thumbnail,
        )
    }
}