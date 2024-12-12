package com.masorange.tecnictest.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.masorange.tecnictest.api.createImgurApi
import com.masorange.tecnictest.ui.components.Camera
import com.masorange.tecnictest.viewmodels.BaseViewModel
import com.masorange.tecnictest.viewmodels.CameraViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

object CameraViewModelImp : BaseViewModel<CameraViewModel>(CameraViewModel::class.java)

@Composable
fun ImageScreen(navController: NavHostController, accessToken: String, username:String) {
    val context = LocalContext.current
    val service = createImgurApi(accessToken)
    val cameraViewModel = CameraViewModelImp.getInstance()
    Camera { bitmap ->
        cameraViewModel.onTakePhoto(bitmap)
        cameraViewModel.onSelectBitmap(bitmap)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val file= bitmapToFile(context = context,bitmap)
                val body = createRequestBody(file)
                service.uploadImage("Bearer $accessToken", body)
            } catch (e: Exception) {
                e.printStackTrace() // Manejar excepciones
            }
        }
        navController.navigateUp()

    }
}
fun bitmapToFile(context: Context, bitmap: Bitmap): File {
    val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.jpg")
    file.createNewFile()

    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

    return file
}
fun createRequestBody(file: File): MultipartBody.Part {
    val requestFile = RequestBody.create("image/jpeg".toMediaType(), file)
    return MultipartBody.Part.createFormData("image", file.name, requestFile)
}

