package com.masorange.tecnictest.api.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImgurRequest(
    @SerializedName("data")
    val data: List<ImageImgur>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("status")
    val status: Int
): Serializable
