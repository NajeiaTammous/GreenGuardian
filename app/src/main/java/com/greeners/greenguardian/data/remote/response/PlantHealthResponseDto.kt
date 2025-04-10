package com.greeners.greenguardian.data.remote.response

import com.google.gson.annotations.SerializedName
import com.greeners.greenguardian.domain.model.PlantHealthDetails

data class PlantHealthResponseDto(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("model_version") val modelVersion: String,
    @SerializedName("custom_id") val customId: String?,
    @SerializedName("input") val input: InputDataDto,
    @SerializedName("result") val result: ResultDataDto,
    @SerializedName("status") val status: String,
    @SerializedName("sla_compliant_client") val slaCompliantClient: Boolean,
    @SerializedName("sla_compliant_system") val slaCompliantSystem: Boolean,
    @SerializedName("created") val created: Double,
    @SerializedName("completed") val completed: Double
)

data class InputDataDto(
    @SerializedName("health") val health: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("similar_images") val similarImages: Boolean
)

data class ResultDataDto(
    @SerializedName("disease") val disease: DiseaseDataDto,
    @SerializedName("is_plant") val isPlant: BinaryResult,
    @SerializedName("is_healthy") val isHealthy: BinaryResult
) {

    fun toEntity(): PlantHealthDetails {
        val primarySuggestion = disease.suggestions.firstOrNull()
        val treatment = primarySuggestion?.details?.treatment

        return PlantHealthDetails(
            id = primarySuggestion?.id ?: "",
            similarDiseaseImages = primarySuggestion?.similarImages?.map { it.url }
                ?: emptyList(),
            diseaseName = primarySuggestion?.name ?: "",
            description = primarySuggestion?.details?.description ?: "",
            chemicalTreatment = treatment?.chemical?.joinToString("\n") ?: "",
            biologicalTreatment = treatment?.biological?.joinToString("\n") ?: "",
            preventionTreatment = treatment?.prevention?.joinToString("\n") ?: ""
        )
    }

}

data class DiseaseDataDto(
    @SerializedName("suggestions") val suggestions: List<SuggestionDto>,
    @SerializedName("question") val question: QuestionData?
)

data class SuggestionDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("probability") val probability: Double,
    @SerializedName("similar_images") val similarImages: List<SimilarImageDto>,
    @SerializedName("details") val details: SuggestionDetailsDto
)

data class SimilarImageDto(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("license_name") val licenseName: String,
    @SerializedName("license_url") val licenseUrl: String,
    @SerializedName("citation") val citation: String,
    @SerializedName("similarity") val similarity: Double,
    @SerializedName("url_small") val urlSmall: String
)

data class SuggestionDetailsDto(
    @SerializedName("local_name") val localName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("treatment") val treatment: Treatment?,
    @SerializedName("classification") val classification: List<String>,
    @SerializedName("common_names") val commonNames: List<String>?,
    @SerializedName("cause") val cause: String?,
    @SerializedName("language") val language: String,
    @SerializedName("entity_id") val entityId: String
)

data class Treatment(
    @SerializedName("chemical") val chemical: List<String>?,
    @SerializedName("biological") val biological: List<String>?,
    @SerializedName("prevention") val prevention: List<String>?
)

data class QuestionData(
    @SerializedName("text") val text: String,
    @SerializedName("translation") val translation: String,
    @SerializedName("options") val options: QuestionOptions
)

data class QuestionOptions(
    @SerializedName("yes") val yes: QuestionOption,
    @SerializedName("no") val no: QuestionOption
)

data class QuestionOption(
    @SerializedName("suggestion_index") val suggestionIndex: Int,
    @SerializedName("entity_id") val entityId: String,
    @SerializedName("name") val name: String,
    @SerializedName("translation") val translation: String
)

data class BinaryResult(
    @SerializedName("binary") val binary: Boolean,
    @SerializedName("threshold") val threshold: Double,
    @SerializedName("probability") val probability: Double
)