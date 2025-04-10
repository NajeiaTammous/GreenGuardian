package com.greeners.greenguardian.data.remote.service

import com.greeners.greenguardian.data.remote.request.PlantDiseaseDto
import com.greeners.greenguardian.data.remote.request.PlantDiseaseRequestDto
import com.greeners.greenguardian.data.remote.response.PlantDetailsResponseDto
import com.greeners.greenguardian.data.remote.response.PlantHealthResponseDto
import com.greeners.greenguardian.data.remote.response.PlantSearchDto
import com.greeners.greenguardian.data.remote.util.NetworkQueryConstants.DETAILS
import com.greeners.greenguardian.data.remote.util.NetworkQueryConstants.LANGUAGE
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface GreenGuardianApiService {

    @POST("health_assessment")
    suspend fun detectPlantHealth(
        @Query("language") language: String? = "en",
        @Query("async") async: Boolean? = true,
        @Query("full_disease_list") fullDiseaseList: Boolean? = true,
        @Query("details") details: String = "local_name,common_names,url,treatment,description,taxonomy,classification,rank,cause,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering",
        @Body request: PlantDiseaseRequestDto
    ): Response<PlantHealthResponseDto>
    @POST("identification")
    suspend fun identifyPlant(
        @Query("language") language: String? = "en",
        @Query("details") details: String = "local_name,common_names,url,treatment,description,taxonomy,classification,rank,cause,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering",
        @Body request: PlantDiseaseRequestDto
    ): Response<PlantHealthResponseDto>

    @GET("identification/{access_token}")
    suspend fun getPlantIdentification(
        @Path("access_token") accessToken: String,
        @Query("language") language: String? = LANGUAGE,
        @Query("details") details: String = DETAILS,
    ): Response<PlantDiseaseDto>

    @Multipart
    @POST("health_assessment")
    suspend fun detectPlantDisease(
        @Query("language") language: String? = "en",
        @Query("async") async: Boolean? = false,
        @Query("details") details: String = "local_name,common_names,url,treatment,description,taxonomy,classification,rank,cause,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering",
        @Part images: List<MultipartBody.Part>,
        @Part("latitude") latitude: Double,
        @Part("longitude") longitude: Double,
        @Part("similar_images") similarImages: Boolean = true,
    ): Response<PlantDiseaseDto>

    @GET("identification/{access_token}")
    suspend fun getPlantHealthDetails(
        @Path("access_token") accessToken: String,
        @Query("language") language: String? = "en",
        @Query("details") details: String = "local_name,common_names,url,treatment,description,taxonomy,classification,rank,cause,gbif_id,inaturalist_id,image,synonyms,edible_parts,watering",
    ): Response<PlantHealthResponseDto>

    @GET("kb/plants/{access_token}")
    suspend fun getPlantDetails(
        @Path("access_token") accessToken: String,
        @Query("language") language: String? = "en",
        @Query("details") details: String = "url,taxonomy,rank,inaturalist_id,gbif_id,image,images,synonyms,edible_parts,watering,best_watering,common_names,description,best_light_condition,best_soil_type,common_uses,cultural_significance,toxicity,propagation_methods",
    ): Response<PlantDetailsResponseDto>

    @GET("kb/plants/name_search")
    suspend fun findPlantByName(
        @Query("q") plantName: String,
        @Query("limit") limit: Int = 20,
        @Query("language") language: String? = "en",
        @Query("thumbnails") thumbnails: Boolean = true
    ): Response<PlantSearchDto>
}
