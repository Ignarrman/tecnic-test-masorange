package com.masorange.tecnictest.api.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ImageImgur(
    @SerializedName("id")
    val data: String,
    @SerializedName("link")
    val link:String,
    @SerializedName("deletehash")
    val deleteHash:String
): Serializable

