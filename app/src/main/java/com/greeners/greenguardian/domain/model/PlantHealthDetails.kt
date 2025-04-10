package com.greeners.greenguardian.domain.model

data class PlantHealthDetails(
    val id: String,
    val similarDiseaseImages: List<String>,
    val diseaseName: String,
    val description: String,
    val chemicalTreatment: String,
    val biologicalTreatment: String,
    val preventionTreatment: String,
    )
