package com.greeners.greenguardian.di

import com.greeners.greenguardian.data.remote.service.GreenGuardianApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val PLANT_ID_API_KEY = "Bozp0SkqtBEkxwZqDxoAKqGK5hPeoZqajJ8CFZYcDQs0coeZGa"

val NetworkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Api-Key", PLANT_ID_API_KEY)
                    .build()
                chain.proceed(request)
            }
            .callTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://plant.id/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<GreenGuardianApiService> {
        get<Retrofit>().create(GreenGuardianApiService::class.java)
    }
}