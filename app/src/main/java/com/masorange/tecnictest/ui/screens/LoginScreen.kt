package com.masorange.tecnictest.ui.screens

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

// val clientSecret = "a1fcafeec5311f2694d5683a1c78abca8b12ffb2"

@Composable
fun LoginScreen(navController: NavHostController) {
    val clientId = "d76ec039286eed6"
    val responseType = "token"
    val url = "https://api.imgur.com/oauth2/authorize?client_id=$clientId&response_type=$responseType"

    // Estado para evitar manejar múltiples redirecciones
    var isRedirectHandled by remember { mutableStateOf(false) }

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val uri = request?.url
                        Log.d("URI",uri.toString())

                        if (uri != null && uri.toString().startsWith("https://www.postman.com/oauth2/callback/")) {
                            if (!isRedirectHandled) {
                                isRedirectHandled = true // Marcar como manejado

                                // Manejar el fragmento manualmente
                                val fragment = uri.fragment
                                if (fragment != null) {
                                    val params = fragment.split("&")
                                        .map { it.split("=") }
                                        .associate { it[0] to it[1] }

                                    val accessToken = params["access_token"]
                                    val accountUsername = params["account_username"]

                                    if (accessToken != null && accountUsername != null) {
                                        Log.d("Access Token", accessToken)
                                        Log.d("Account Username", accountUsername)
                                        // Guardar los datos o pasarlos a la pantalla siguiente
                                        navController.navigate("main/${accessToken}/${accountUsername}") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    } else {
                                        println("Error: Unable to extract access token or username")
                                    }
                                } else {
                                    println("Error: No fragment in URI")
                                }
                            }
                            return true // Detener la carga de la URL en el WebView
                        }
                        return false
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        }
    )
}

