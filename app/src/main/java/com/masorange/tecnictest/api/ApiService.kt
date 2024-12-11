package com.masorange.tecnictest.api

import com.masorange.tecnictest.api.models.ImageImgur
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


// Images
// Login (Usando oauth, avisar de error de credenciales o token expirado)
// Upload
// Delete

interface ApiService{

    @GET("https://api.imgur.com/3/account/me/images")
    suspend fun getMyImages(@Header("Authorization") header: String): List<ImageImgur>

    @POST("https://api.imgur.com/oauth2/authorize")
    suspend fun getAccesToken(@Query("client_id") clientId: String, @Query("response_type") responseType: String)
}

fun createImgurApi(accessToken: String): ApiService {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.imgur.com/") // Base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}

