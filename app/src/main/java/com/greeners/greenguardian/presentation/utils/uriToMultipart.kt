package com.greeners.greenguardian.presentation.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun Uri.toMultipart(context: Context, partName: String): MultipartBody.Part? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(this) ?: return null
    val fileName = getFileName(this, context) ?: "image.jpg"

    val requestBody = inputStream.readBytes()
        .toRequestBody("image/*".toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(partName, fileName, requestBody)
}