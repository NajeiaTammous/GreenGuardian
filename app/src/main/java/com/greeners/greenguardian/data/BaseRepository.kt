package com.greeners.greenguardian.data

import android.util.Log
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T : Any> wrap(function: suspend () -> Response<T>): T {
        val response = function()
        if (response.isSuccessful) {
            Log.e("GreenGuardianRepositoryImpl", "Response: ${response.body()}")
            response.body()?.let { body ->
                return body
            } ?: throw Exception("Response body is null")
        } else {
            throw Exception("Error: ${response.errorBody()?.string()}")
        }
    }

}
