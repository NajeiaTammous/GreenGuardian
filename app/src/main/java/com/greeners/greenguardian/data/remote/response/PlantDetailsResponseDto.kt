package com.greeners.greenguardian.data.remote.response


import com.greeners.greenguardian.domain.model.PlantDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlantDetailsResponseDto(
    @SerialName("best_light_condition")
    val bestLightCondition: String?,
    @SerialName("best_soil_type")
    val bestSoilType: String?,
    @SerialName("best_watering")
    val bestWatering: String?,
    @SerialName("common_names")
    val commonNames: List<String>?,
    @SerialName("common_uses")
    val commonUses: String?,
    @SerialName("cultural_significance")
    val culturalSignificance: String?,
    @SerialName("description")
    val description: Description?,
    @SerialName("edible_parts")
    val edibleParts: List<String>?,
    @SerialName("entity_id")
    val entityId: String?,
    @SerialName("gbif_id")
    val gbifId: Int?,
    @SerialName("image")
    val image: Image?,
    @SerialName("images")
    val images: List<Image>?,
    @SerialName("inaturalist_id")
    val inaturalistId: Int?,
    @SerialName("language")
    val language: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("propagation_methods")
    val propagationMethods: List<String>?,
    @SerialName("rank")
    val rank: String?,
    @SerialName("synonyms")
    val synonyms: List<String>?,
    @SerialName("taxonomy")
    val taxonomy: Taxonomy?,
    @SerialName("toxicity")
    val toxicity: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("watering")
    val watering: Watering?
) {
    fun toEntity(): PlantDetails {
        return PlantDetails(
            id = entityId ?: "",
            name = name ?: "",
            description = description?.value ?: "",
            images = images?.map { it.value ?: "" } ?: emptyList(),
            genus = taxonomy?.classX ?: taxonomy?.family ?: "",
            seeds = toxicity?.takeWhile { it != '.' } ?: "Best in Feb1- Mar18",
            water = bestWatering?.takeWhile { it != '.' } ?: ("sparingly, let the soil dry out\n" +
                    "completely between waterings."),
            pesticide = commonUses?.takeWhile { it != '.' } ?: "lightly spray foliage(5cm)",
        )
    }
}