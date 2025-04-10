package com.greeners.greenguardian.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Watering(
    @SerialName("max")
    val max: Int?,
    @SerialName("min")
    val min: Int?
)