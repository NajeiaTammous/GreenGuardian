package com.greeners.greenguardian.domain.model

data class IdentificationData(
    val imageByteArray: ByteArray,
    val latitude: Double,
    val longitude: Double,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IdentificationData

        if (!imageByteArray.contentEquals(other.imageByteArray)) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imageByteArray.contentHashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        return result
    }
}