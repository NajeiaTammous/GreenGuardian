package com.greeners.greenguardian.data.remote.request


import android.util.Log
import com.google.gson.annotations.SerializedName
import com.greeners.greenguardian.domain.model.PlantDiseaseData

data class PlantDiseaseDto(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("completed")
    val completed: Double?,
    @SerializedName("created")
    val created: Double?,
    @SerializedName("custom_id")
    val customId: String?,
    @SerializedName("input")
    val input: Input?,
    @SerializedName("model_version")
    val modelVersion: String?,
    @SerializedName("result")
    val result: Result?,
    @SerializedName("sla_compliant_client")
    val slaCompliantClient: Boolean?,
    @SerializedName("sla_compliant_system")
    val slaCompliantSystem: Boolean?,
    @SerializedName("status")
    val status: String?
) {
    fun toEntity(): PlantDiseaseData {
        Log.d("TAG","Access token: $accessToken")
        val disease = this.result?.disease?.suggestions?.first()
        val hmm = PlantDiseaseData(
            id = disease?.id ?: "0",
            name = disease?.name ?: "",
            similarImages = disease?.similarImages?.map {
                it?.url
                    ?: "https://plant-id.ams3.cdn.digitaloceanspaces.com/similar_images/4/cbb/3aac34fdd574cc5ff9f3b6713e425802b39e7.jpg"
            } ?: emptyList(),
            description = disease?.details?.description ?: "",
            treatment = disease?.details?.treatment?.toEntity() ?: PlantDiseaseData.Treatment(),
            accessToken = accessToken ?: "",
            isHealthy = result?.isHealthy?.binary == true,
            isPlant = result?.isPlant?.binary == true
        )
        Log.d("TAG", hmm.toString())
        return hmm

    }

    data class Input(
        @SerializedName("datetime")
        val datetime: String?,
        @SerializedName("health")
        val health: String?,
        @SerializedName("images")
        val images: List<String?>?,
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?,
        @SerializedName("similar_images")
        val similarImages: Boolean?
    )

    data class Result(
        @SerializedName("disease")
        val disease: Disease?,
        @SerializedName("is_healthy")
        val isHealthy: IsHealthy?,
        @SerializedName("is_plant")
        val isPlant: IsPlant?
    ) {
        data class Disease(
            @SerializedName("question")
            val question: Question?,
            @SerializedName("suggestions")
            val suggestions: List<Suggestion?>?
        ) {
            data class Question(
                @SerializedName("options")
                val options: Options?,
                @SerializedName("text")
                val text: String?
            ) {
                data class Options(
                    @SerializedName("no")
                    val no: No?,
                    @SerializedName("yes")
                    val yes: Yes?
                ) {
                    data class No(
                        @SerializedName("entity_id")
                        val entityId: String?,
                        @SerializedName("name")
                        val name: String?,
                        @SerializedName("suggestion_index")
                        val suggestionIndex: Int?
                    )

                    data class Yes(
                        @SerializedName("entity_id")
                        val entityId: String?,
                        @SerializedName("name")
                        val name: String?,
                        @SerializedName("suggestion_index")
                        val suggestionIndex: Int?
                    )
                }
            }

            data class Suggestion(
                @SerializedName("details")
                val details: Details?,
                @SerializedName("id")
                val id: String?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("probability")
                val probability: Double?,
                @SerializedName("similar_images")
                val similarImages: List<SimilarImage?>?
            ) {
                data class Details(
                    @SerializedName("cause")
                    val cause: String?,
                    @SerializedName("classification")
                    val classification: List<String?>?,
                    @SerializedName("common_names")
                    val commonNames: List<String?>?,
                    @SerializedName("description")
                    val description: String?,
                    @SerializedName("entity_id")
                    val entityId: String?,
                    @SerializedName("language")
                    val language: String?,
                    @SerializedName("local_name")
                    val localName: String?,
                    @SerializedName("treatment")
                    val treatment: Treatment?,
                    @SerializedName("url")
                    val url: String?
                ) {
                    data class Treatment(
                        @SerializedName("biological")
                        val biological: List<String?>?,
                        @SerializedName("chemical")
                        val chemical: List<String?>?,
                        @SerializedName("prevention")
                        val prevention: List<String?>?
                    ) {
                        fun toEntity(): PlantDiseaseData.Treatment {
                            return PlantDiseaseData.Treatment(
                                biological = this.biological?.map { it ?: "" }
                                    ?: emptyList(),
                                chemical = this.chemical?.map { it ?: "" } ?: emptyList(),
                                prevention = this.prevention?.map { it ?: "" } ?: emptyList()
                            )
                        }
                    }
                }

                data class SimilarImage(
                    @SerializedName("citation")
                    val citation: String?,
                    @SerializedName("id")
                    val id: String?,
                    @SerializedName("license_name")
                    val licenseName: String?,
                    @SerializedName("license_url")
                    val licenseUrl: String?,
                    @SerializedName("similarity")
                    val similarity: Double?,
                    @SerializedName("url")
                    val url: String?,
                    @SerializedName("url_small")
                    val urlSmall: String?
                )
            }
        }

        data class IsHealthy(
            @SerializedName("binary")
            val binary: Boolean?,
            @SerializedName("probability")
            val probability: Double?,
            @SerializedName("threshold")
            val threshold: Double?
        )

        data class IsPlant(
            @SerializedName("binary")
            val binary: Boolean?,
            @SerializedName("probability")
            val probability: Double?,
            @SerializedName("threshold")
            val threshold: Double?
        )
    }
}