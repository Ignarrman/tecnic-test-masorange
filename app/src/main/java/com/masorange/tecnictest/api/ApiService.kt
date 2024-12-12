package com.masorange.tecnictest.api

import android.graphics.Bitmap
import com.masorange.tecnictest.api.models.ImgurRequest
import com.masorange.tecnictest.api.models.ImgurUploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


// Images
// Login (Usando oauth, avisar de error de credenciales o token expirado)
// Upload
// Delete

interface ApiService{

    @GET("https://api.imgur.com/3/account/me/images")
    suspend fun getMyImages(@Header("Authorization") header: String): ImgurRequest

    @Multipart
    @POST("3/image")
    suspend fun uploadImage(@Header("Authorization") authorization: String, @Part image: MultipartBody.Part): Response<ImgurUploadResponse>

    @DELETE("https://api.imgur.com/3/account/{username}/image/{deleteHash}")
    suspend fun deleteImage(@Header("Authorization") authorization: String, @Path("username") username:String, @Path("deleteHash") deleteHash:String): Response<ImgurUploadResponse>

}

fun createImgurApi(accessToken: String): ApiService {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.imgur.com/") // Base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}

