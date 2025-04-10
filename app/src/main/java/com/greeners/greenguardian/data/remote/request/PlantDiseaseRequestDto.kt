package com.greeners.greenguardian.data.remote.request

import com.google.gson.annotations.SerializedName

data class PlantDiseaseRequestDto(
    @SerializedName("images") val images: List<String>,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("similar_images") val similarImages: Boolean
)