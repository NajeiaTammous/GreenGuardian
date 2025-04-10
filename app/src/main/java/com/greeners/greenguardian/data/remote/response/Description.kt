package com.greeners.greenguardian.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Description(
    @SerialName("citation")
    val citation: String?,
    @SerialName("license_name")
    val licenseName: String?,
    @SerialName("license_url")
    val licenseUrl: String?,
    @SerialName("value")
    val value: String?
)