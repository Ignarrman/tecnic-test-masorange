package com.masorange.tecnictest.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.masorange.tecnictest.api.models.ImageImgur
import com.masorange.tecnictest.api.createImgurApi

@Composable
fun MainScreen(navController: NavHostController, accessToken: String, username: String){
    val service = createImgurApi(accessToken)
    var images by remember { mutableStateOf(listOf<ImageImgur>()) }

    LaunchedEffect(Unit) {
        try {
            images = service.getMyImages("Client-ID $accessToken")
        } catch (e: Exception) {
            Log.d("ERROR IMGUR","Error fetching images: ${e.message}")
        }
    }

    Column {
        for(image in images){
            Text(image.link)
        }
        Text(accessToken)
        Text(username)
    }
}