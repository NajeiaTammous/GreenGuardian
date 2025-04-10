package com.greeners.greenguardian.data.remote.response

import com.google.gson.annotations.SerializedName

data class GreenGuardianResponse(
    @SerializedName("user_id")
    val id: String,
)
