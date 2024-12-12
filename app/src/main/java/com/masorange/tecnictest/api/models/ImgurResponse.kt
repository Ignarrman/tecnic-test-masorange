package com.masorange.tecnictest.api.models

data class ImgurUploadResponse(
    val data: ImgurData,
    val success: Boolean,
    val status: Int
)

data class ImgurData(
    val id: String,
    val link: String
)
