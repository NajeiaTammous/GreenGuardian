package com.greeners.greenguardian.presentation.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun getFileName(uri: Uri, context: Context): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        it.getString(nameIndex)
    }
}