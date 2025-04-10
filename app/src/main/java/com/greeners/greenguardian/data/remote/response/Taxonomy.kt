package com.greeners.greenguardian.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Taxonomy(
    @SerialName("class")
    val classX: String?,
    @SerialName("family")
    val family: String?,
    @SerialName("genus")
    val genus: String?,
    @SerialName("kingdom")
    val kingdom: String?,
    @SerialName("order")
    val order: String?,
    @SerialName("phylum")
    val phylum: String?
)