package com.masorange.tecnictest.api.models

import com.google.gson.annotations.SerializedName

data class ImageImgur(
    @SerializedName("id")
    val id: String,
    @SerializedName("link")
    val link:String
)
