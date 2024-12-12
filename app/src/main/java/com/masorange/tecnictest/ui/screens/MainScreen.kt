package com.masorange.tecnictest.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.masorange.tecnictest.api.createImgurApi
import com.masorange.tecnictest.api.models.ImageImgur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun MainScreen(navController: NavHostController, accessToken: String, username: String) {
    val service = createImgurApi(accessToken)
    var images by remember { mutableStateOf(listOf<ImageImgur>()) }
    val reload  = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        try {
            val data = service.getMyImages("Bearer $accessToken")
            images = data.data
        } catch (e: Exception) {
            Log.d("ERROR IMGUR", "Error fetching images: ${e.message}")
        }
    }

    Column(Modifier.fillMaxSize().background(Color(31, 50, 161, 255))) {
        Row{
            Button(onClick = {
                navController.navigate("camera/${accessToken}/${username}") {
                }
            },
                colors = ButtonDefaults.buttonColors(Color(33, 245, 115, 216))
            ) { Text("Hacer foto",
                fontWeight = FontWeight.Bold,) }

            Button(onClick = {
                navController.navigate("import/${accessToken}/${username}") {
                }
            },
                colors = ButtonDefaults.buttonColors(Color(33, 245, 115, 216))
            ) { Text("Importar del sistema de archivos",
                fontWeight = FontWeight.Bold) }

            IconButton(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val data = service.getMyImages("Bearer $accessToken")
                        images = data.data
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh icon",
                    tint = Color.White,
                    modifier = Modifier.background(Color(33, 245, 115, 216),
                        RoundedCornerShape(4.dp)
                    )
                )
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp).background(Color(
            39,
            67,
            224,
            255
        )
        )) {
            items(images.chunked(3)) { rowImages ->
                Row(Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    rowImages.forEach { image ->
                        Box(
                            contentAlignment = Alignment.TopEnd,
                            modifier = Modifier
                                .background(color = Color(33, 245, 115, 216))
                                .size(100.dp)
                                .padding(4.dp)
                        ) {
                            AsyncImage(
                                model = image.link,
                                contentDescription = "Image description",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(100.dp)
                            )
                            // Ícono de papelera en la esquina superior derecha
                            IconButton(
                                onClick = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        try {
                                            service.deleteImage("Bearer $accessToken", username, image.deleteHash)
                                            reload.intValue++
                                        } catch (e: Exception) {
                                            e.printStackTrace() // Manejar excepciones
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete icon",
                                    tint = Color.Red,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                )
                            }
                        }
                    }
                    repeat(3 - rowImages.size) {
                        Spacer(modifier = Modifier.size(100.dp).padding(4.dp))
                    }
                }
            }
        }
    }
}
