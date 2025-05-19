package com.example.fotosdemarte.network

import com.example.fotosdemarte.model.MarsPhotoResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsApiService {
    @GET("rovers/curiosity/photos")
    suspend fun getPhotos(
        @Query("sol") sol: Int = 1000,
        @Query("api_key") apiKey: String = "WINARhzMafyZYh55GW3w4LmenQTGD88dK0zeoFPQ"
    ): MarsPhotoResponse
}

object MarsApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

